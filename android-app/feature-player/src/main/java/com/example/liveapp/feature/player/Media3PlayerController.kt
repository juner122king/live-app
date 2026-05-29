package com.example.liveapp.feature.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.HttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class Media3PlayerController(context: Context) {
    private val player = ExoPlayer.Builder(context).build()
    private var onPlaybackError: ((String) -> Unit)? = null

    init {
        player.addListener(
            object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    onPlaybackError?.invoke(error.toDisplayMessage())
                }
            }
        )
    }

    fun attach(playerView: PlayerView) {
        playerView.player = player
    }

    fun setOnPlaybackError(listener: ((String) -> Unit)?) {
        onPlaybackError = listener
    }

    fun play(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.play()
    }

    fun stop() {
        player.release()
    }
}

private fun PlaybackException.toDisplayMessage(): String {
    val httpError = findCause<HttpDataSource.InvalidResponseCodeException>()
    if (httpError != null) {
        return "HTTP ${httpError.responseCode}"
    }
    return message ?: errorCodeName
}

private inline fun <reified T : Throwable> Throwable.findCause(): T? {
    var current: Throwable? = this
    while (current != null) {
        if (current is T) return current
        current = current.cause
    }
    return null
}

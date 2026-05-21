package com.example.liveapp.feature.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import androidx.media3.exoplayer.ExoPlayer

class Media3PlayerController(context: Context) {
    private val player = ExoPlayer.Builder(context).build()

    fun attach(playerView: PlayerView) {
        playerView.player = player
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


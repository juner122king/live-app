package com.example.liveapp.feature.player

import androidx.lifecycle.ViewModel
import androidx.media3.ui.PlayerView
import com.example.liveapp.core.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PlayerUiState(
    val room: Room? = null,
    val errorMessage: String? = null,
)

class PlayerViewModel(
    private val playerController: Media3PlayerController,
) : ViewModel() {
    fun attachPlayerView(playerView: PlayerView) {
        playerController.attach(playerView)
    }

    fun releasePlayer() {
        playerController.stop()
    }

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    fun bindRoom(room: Room) {
        _uiState.value = _uiState.value.copy(room = room)
        playerController.play(room.playUrl)
    }

    override fun onCleared() {
        playerController.stop()
    }
}

package com.example.liveapp.feature.publish

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveapp.core.model.Room
import com.example.liveapp.core.network.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PublishUiState(
    val room: Room? = null,
    val isLive: Boolean = false,
    val errorMessage: String? = null,
)

class PublishViewModel(
    private val roomRepository: RoomRepository,
    private val publisherController: NodeMediaPublisherController,
) : ViewModel() {
    fun attachPreview(container: ViewGroup) {
        publisherController.attachPreview(container)
    }

    fun stopPreview() {
        publisherController.stopPreview()
    }
    private val _uiState = MutableStateFlow(PublishUiState())
    val uiState: StateFlow<PublishUiState> = _uiState.asStateFlow()

    fun bindRoom(room: Room) {
        _uiState.value = _uiState.value.copy(room = room)
    }

    fun startLive() {
        val room = _uiState.value.room ?: return
        viewModelScope.launch {
            runCatching {
                roomRepository.startRoom(room.id)
                publisherController.startPublish(room.pushUrl)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLive = true, errorMessage = null)
            }.onFailure {
                _uiState.value = _uiState.value.copy(errorMessage = it.message ?: "开播失败")
            }
        }
    }

    fun stopLive() {
        val room = _uiState.value.room ?: return
        viewModelScope.launch {
            runCatching {
                publisherController.stopPublish()
                roomRepository.stopRoom(room.id)
            }.onSuccess {
                _uiState.value = _uiState.value.copy(isLive = false, errorMessage = null)
            }.onFailure {
                _uiState.value = _uiState.value.copy(errorMessage = it.message ?: "下播失败")
            }
        }
    }

    fun switchCamera() {
        publisherController.switchCamera()
    }
}

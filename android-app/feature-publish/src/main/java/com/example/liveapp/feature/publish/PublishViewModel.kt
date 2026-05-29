package com.example.liveapp.feature.publish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveapp.core.model.Room
import com.example.liveapp.core.network.repository.RoomRepository
import com.pedro.library.view.OpenGlView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PublishUiState(
    val room: Room? = null,
    val isLive: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
)

class PublishViewModel(
    private val roomRepository: RoomRepository,
    private val publisherController: RtmpPublisherController,
) : ViewModel() {
    private var previewStarted = false
    private val _uiState = MutableStateFlow(PublishUiState())
    val uiState: StateFlow<PublishUiState> = _uiState.asStateFlow()

    init {
        publisherController.connectionListener = object : PublisherConnectionListener {
            override fun onConnectionSuccess() {
                _uiState.update {
                    it.copy(isLive = true, isConnecting = false, errorMessage = null)
                }
            }

            override fun onConnectionFailed(reason: String) {
                _uiState.update {
                    it.copy(isLive = false, isConnecting = false, errorMessage = "推流连接失败：$reason")
                }
                markRoomOffline()
            }

            override fun onDisconnect() {
                _uiState.update {
                    it.copy(isLive = false, isConnecting = false)
                }
            }
        }
    }

    fun attachPreview(openGlView: OpenGlView) {
        if (previewStarted) return
        runCatching {
            publisherController.attachPreview(openGlView)
        }.onSuccess {
            previewStarted = true
            _uiState.update { it.copy(errorMessage = null) }
        }.onFailure { throwable ->
            previewStarted = false
            _uiState.update { it.copy(errorMessage = throwable.message ?: "预览启动失败") }
        }
    }

    fun stopPreview() {
        publisherController.stopPreview()
        previewStarted = false
    }

    fun bindRoom(room: Room) {
        _uiState.update { it.copy(room = room) }
    }

    fun startLive() {
        val room = _uiState.value.room ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(isConnecting = true, errorMessage = null) }
            runCatching {
                roomRepository.startRoom(room.id)
                publisherController.startPublish(room.pushUrl)
            }.onFailure {
                _uiState.update { state ->
                    state.copy(isLive = false, isConnecting = false, errorMessage = it.message ?: "开播失败")
                }
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
                _uiState.update { it.copy(isLive = false, isConnecting = false, errorMessage = null) }
            }.onFailure {
                _uiState.update { state ->
                    state.copy(isConnecting = false, errorMessage = it.message ?: "下播失败")
                }
            }
        }
    }

    fun switchCamera() {
        runCatching {
            publisherController.switchCamera()
        }.onFailure { throwable ->
            _uiState.update { it.copy(errorMessage = throwable.message ?: "切换摄像头失败") }
        }
    }

    private fun markRoomOffline() {
        val room = _uiState.value.room ?: return
        viewModelScope.launch {
            runCatching {
                roomRepository.stopRoom(room.id)
            }
        }
    }

    override fun onCleared() {
        publisherController.connectionListener = null
        publisherController.stopPublish()
        publisherController.stopPreview()
    }
}

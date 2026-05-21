package com.example.liveapp.feature.createroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveapp.core.model.Room
import com.example.liveapp.core.network.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateRoomUiState(
    val title: String = "",
    val coverUrl: String = "",
    val isSubmitting: Boolean = false,
    val room: Room? = null,
    val errorMessage: String? = null,
)

class CreateRoomViewModel(
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateRoomUiState())
    val uiState: StateFlow<CreateRoomUiState> = _uiState.asStateFlow()

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun onCoverUrlChange(value: String) {
        _uiState.value = _uiState.value.copy(coverUrl = value)
    }

    fun submit() {
        val state = _uiState.value
        if (state.title.isBlank() || state.coverUrl.isBlank()) {
            _uiState.value = state.copy(errorMessage = "标题和封面地址不能为空")
            return
        }
        viewModelScope.launch {
            _uiState.value = state.copy(isSubmitting = true, errorMessage = null)
            runCatching {
                roomRepository.createRoom(state.title, state.coverUrl)
            }.onSuccess { room ->
                _uiState.value = _uiState.value.copy(isSubmitting = false, room = room)
            }.onFailure {
                _uiState.value = _uiState.value.copy(isSubmitting = false, errorMessage = it.message ?: "创建失败")
            }
        }
    }
}

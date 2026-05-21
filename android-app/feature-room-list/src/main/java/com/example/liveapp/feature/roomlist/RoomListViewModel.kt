package com.example.liveapp.feature.roomlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liveapp.core.model.Room
import com.example.liveapp.core.network.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RoomListUiState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

class RoomListViewModel(
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RoomListUiState())
    val uiState: StateFlow<RoomListUiState> = _uiState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            runCatching { roomRepository.getRooms() }
                .onSuccess { rooms ->
                    _uiState.value = RoomListUiState(rooms = rooms)
                }
                .onFailure {
                    _uiState.value = RoomListUiState(errorMessage = it.message ?: "加载失败")
                }
        }
    }
}

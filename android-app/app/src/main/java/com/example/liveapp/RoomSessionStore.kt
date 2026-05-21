package com.example.liveapp

import com.example.liveapp.core.model.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object RoomSessionStore {
    private val _currentRoom = MutableStateFlow<Room?>(null)
    val currentRoom: StateFlow<Room?> = _currentRoom.asStateFlow()

    fun setRoom(room: Room) {
        _currentRoom.value = room
    }
}

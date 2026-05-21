package com.example.liveapp.core.network.repository

import com.example.liveapp.core.model.Room

interface RoomRepository {
    suspend fun getRooms(): List<Room>
    suspend fun getRoom(id: Long): Room
    suspend fun createRoom(title: String, coverUrl: String): Room
    suspend fun startRoom(id: Long): Room
    suspend fun stopRoom(id: Long): Room
}

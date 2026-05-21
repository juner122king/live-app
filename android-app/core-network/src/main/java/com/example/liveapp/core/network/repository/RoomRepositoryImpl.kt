package com.example.liveapp.core.network.repository

import com.example.liveapp.core.model.LiveStatus
import com.example.liveapp.core.model.Room
import com.example.liveapp.core.network.api.LiveApiService
import com.example.liveapp.core.network.dto.CreateRoomRequestDto
import com.example.liveapp.core.network.dto.RoomDto

class RoomRepositoryImpl(
    private val api: LiveApiService,
) : RoomRepository {
    override suspend fun getRooms(): List<Room> = api.getRooms().map { it.toModel() }

    override suspend fun getRoom(id: Long): Room = api.getRoom(id).toModel()

    override suspend fun createRoom(title: String, coverUrl: String): Room =
        api.createRoom(CreateRoomRequestDto(title = title, coverUrl = coverUrl)).toModel()

    override suspend fun startRoom(id: Long): Room = api.startRoom(id).toModel()

    override suspend fun stopRoom(id: Long): Room = api.stopRoom(id).toModel()

    private fun RoomDto.toModel(): Room = Room(
        id = id,
        title = title,
        coverUrl = coverUrl,
        streamKey = streamKey,
        pushUrl = pushUrl,
        playUrl = playUrl,
        status = LiveStatus.valueOf(status),
    )
}

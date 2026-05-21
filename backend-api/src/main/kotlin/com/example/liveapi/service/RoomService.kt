package com.example.liveapi.service

import com.example.liveapi.config.StreamProperties
import com.example.liveapi.dto.CreateRoomRequest
import com.example.liveapi.dto.RoomResponse
import com.example.liveapi.entity.LiveStatus
import com.example.liveapi.entity.RoomEntity
import com.example.liveapi.repository.RoomJpaRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.util.UUID

@Service
class RoomService(
    private val roomRepository: RoomJpaRepository,
    private val streamProperties: StreamProperties,
) {
    @Transactional
    fun createRoom(request: CreateRoomRequest): RoomResponse {
        val streamKey = UUID.randomUUID().toString()
        val now = LocalDateTime.now()
        val saved = roomRepository.save(
            RoomEntity(
                title = request.title,
                coverUrl = request.coverUrl,
                streamKey = streamKey,
                playUrl = "${streamProperties.hlsBaseUrl}/$streamKey.m3u8",
                status = LiveStatus.OFFLINE,
                createdAt = now,
                updatedAt = now,
            )
        )
        return saved.toResponse(streamProperties)
    }

    @Transactional(readOnly = true)
    fun listRooms(): List<RoomResponse> = roomRepository.findAll().map { it.toResponse(streamProperties) }

    @Transactional(readOnly = true)
    fun getRoom(id: Long): RoomResponse = findRoom(id).toResponse(streamProperties)

    @Transactional
    fun startRoom(id: Long): RoomResponse = updateStatus(id, LiveStatus.LIVE)

    @Transactional
    fun stopRoom(id: Long): RoomResponse = updateStatus(id, LiveStatus.OFFLINE)

    private fun updateStatus(id: Long, status: LiveStatus): RoomResponse {
        val room = findRoom(id)
        val updated = roomRepository.save(room.copy(status = status, updatedAt = LocalDateTime.now()))
        return updated.toResponse(streamProperties)
    }

    private fun findRoom(id: Long): RoomEntity = roomRepository.findById(id).orElseThrow {
        ResponseStatusException(HttpStatus.NOT_FOUND, "Room $id not found")
    }

    private fun RoomEntity.toResponse(streamProperties: StreamProperties): RoomResponse = RoomResponse(
        id = id,
        title = title,
        coverUrl = coverUrl,
        streamKey = streamKey,
        pushUrl = "${streamProperties.rtmpBaseUrl}/$streamKey",
        playUrl = playUrl,
        status = status,
    )
}

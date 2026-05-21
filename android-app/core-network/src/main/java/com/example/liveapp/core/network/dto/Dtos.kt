package com.example.liveapp.core.network.dto

data class CreateRoomRequestDto(
    val title: String,
    val coverUrl: String,
)

data class RoomDto(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val streamKey: String,
    val pushUrl: String,
    val playUrl: String,
    val status: String,
)

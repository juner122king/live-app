package com.example.liveapi.dto

import com.example.liveapi.entity.LiveStatus

data class RoomResponse(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val streamKey: String,
    val pushUrl: String,
    val playUrl: String,
    val status: LiveStatus,
)

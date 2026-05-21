package com.example.liveapp.core.model

data class Room(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val streamKey: String,
    val pushUrl: String,
    val playUrl: String,
    val status: LiveStatus,
)

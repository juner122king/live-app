package com.example.liveapi.dto

import jakarta.validation.constraints.NotBlank

data class CreateRoomRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val coverUrl: String,
)

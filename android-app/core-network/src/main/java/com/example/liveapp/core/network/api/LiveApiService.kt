package com.example.liveapp.core.network.api

import com.example.liveapp.core.network.dto.CreateRoomRequestDto
import com.example.liveapp.core.network.dto.RoomDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LiveApiService {
    @GET("rooms")
    suspend fun getRooms(): List<RoomDto>

    @GET("rooms/{id}")
    suspend fun getRoom(@Path("id") id: Long): RoomDto

    @POST("rooms")
    suspend fun createRoom(@Body request: CreateRoomRequestDto): RoomDto

    @POST("rooms/{id}/start")
    suspend fun startRoom(@Path("id") id: Long): RoomDto

    @POST("rooms/{id}/stop")
    suspend fun stopRoom(@Path("id") id: Long): RoomDto
}

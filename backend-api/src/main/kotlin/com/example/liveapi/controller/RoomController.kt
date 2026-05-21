package com.example.liveapi.controller

import com.example.liveapi.dto.CreateRoomRequest
import com.example.liveapi.dto.RoomResponse
import com.example.liveapi.service.RoomService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val roomService: RoomService,
) {
    @PostMapping
    fun createRoom(@Valid @RequestBody request: CreateRoomRequest): RoomResponse = roomService.createRoom(request)

    @GetMapping
    fun listRooms(): List<RoomResponse> = roomService.listRooms()

    @GetMapping("/{id}")
    fun getRoom(@PathVariable id: Long): RoomResponse = roomService.getRoom(id)

    @PostMapping("/{id}/start")
    fun startRoom(@PathVariable id: Long): RoomResponse = roomService.startRoom(id)

    @PostMapping("/{id}/stop")
    fun stopRoom(@PathVariable id: Long): RoomResponse = roomService.stopRoom(id)
}

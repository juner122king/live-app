package com.example.liveapp

import com.example.liveapp.core.network.repository.NetworkModule
import com.example.liveapp.core.network.repository.RoomRepository

class AppContainer {
    val roomRepository: RoomRepository = NetworkModule.roomRepository
}

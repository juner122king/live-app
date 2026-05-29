package com.example.liveapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.liveapp.core.network.repository.RoomRepository
import com.example.liveapp.feature.createroom.CreateRoomViewModel
import com.example.liveapp.feature.player.Media3PlayerController
import com.example.liveapp.feature.player.PlayerViewModel
import com.example.liveapp.feature.publish.RtmpPublisherController
import com.example.liveapp.feature.publish.PublishViewModel
import com.example.liveapp.feature.roomlist.RoomListViewModel

fun createRoomViewModelFactory(roomRepository: RoomRepository): ViewModelProvider.Factory = simpleFactory {
    CreateRoomViewModel(roomRepository)
}

fun roomListViewModelFactory(roomRepository: RoomRepository): ViewModelProvider.Factory = simpleFactory {
    RoomListViewModel(roomRepository)
}

fun publishViewModelFactory(
    roomRepository: RoomRepository,
    publisherController: RtmpPublisherController,
): ViewModelProvider.Factory = simpleFactory {
    PublishViewModel(roomRepository, publisherController)
}

fun playerViewModelFactory(
    playerController: Media3PlayerController,
): ViewModelProvider.Factory = simpleFactory {
    PlayerViewModel(playerController)
}

private inline fun <reified T : ViewModel> simpleFactory(
    crossinline create: () -> T,
): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return create() as VM
    }
}

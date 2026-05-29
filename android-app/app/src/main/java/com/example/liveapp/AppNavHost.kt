package com.example.liveapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.liveapp.feature.createroom.CreateRoomScreen
import com.example.liveapp.feature.createroom.CreateRoomViewModel
import com.example.liveapp.feature.player.Media3PlayerController
import com.example.liveapp.feature.player.PlayerScreen
import com.example.liveapp.feature.player.PlayerViewModel
import com.example.liveapp.feature.publish.RtmpPublisherController
import com.example.liveapp.feature.publish.PublishScreen
import com.example.liveapp.feature.publish.PublishViewModel
import com.example.liveapp.feature.roomlist.RoomListScreen
import com.example.liveapp.feature.roomlist.RoomListViewModel

object Routes {
    const val RoomList = "roomList"
    const val CreateRoom = "createRoom"
    const val Publish = "publish"
    const val Player = "player"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val appContainer = (context.applicationContext as LiveApplication).appContainer
    val currentRoom by RoomSessionStore.currentRoom.collectAsState()
    val roomListViewModel = remember { RoomListViewModel(appContainer.roomRepository) }
    val createRoomViewModel = remember { CreateRoomViewModel(appContainer.roomRepository) }
    val publishViewModel = remember {
        PublishViewModel(
            appContainer.roomRepository,
            RtmpPublisherController(context)
        )
    }
    val playerViewModel = remember { PlayerViewModel(Media3PlayerController(context)) }

    NavHost(navController = navController, startDestination = Routes.RoomList) {
        composable(Routes.RoomList) {
            RoomListScreen(
                viewModel = roomListViewModel,
                onCreateRoomClick = { navController.navigate(Routes.CreateRoom) },
                onRoomClick = { room ->
                    RoomSessionStore.setRoom(room)
                    navController.navigate(Routes.Player)
                }
            )
        }
        composable(Routes.CreateRoom) {
            CreateRoomScreen(
                viewModel = createRoomViewModel,
                onBackClick = { navController.popBackStack() },
                onRoomCreated = { room ->
                    RoomSessionStore.setRoom(room)
                    navController.navigate(Routes.Publish)
                }
            )
        }
        composable(Routes.Publish) {
            PublishScreen(
                viewModel = publishViewModel,
                room = currentRoom,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Routes.Player) {
            PlayerScreen(
                viewModel = playerViewModel,
                room = currentRoom,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

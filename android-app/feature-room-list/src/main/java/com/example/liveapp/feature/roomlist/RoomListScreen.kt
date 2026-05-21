package com.example.liveapp.feature.roomlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.liveapp.core.model.Room

@Composable
fun RoomListScreen(
    onCreateRoomClick: () -> Unit,
    onRoomClick: (Room) -> Unit,
    viewModel: RoomListViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(onClick = onCreateRoomClick, modifier = Modifier.fillMaxWidth()) {
            Text("创建直播间")
        }
        state.errorMessage?.let { Text(text = it) }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.rooms, key = Room::id) { room ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRoomClick(room) }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        AsyncImage(
                            model = room.coverUrl,
                            contentDescription = room.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                        )
                        Text(text = room.title)
                        Text(text = room.status.name)
                    }
                }
            }
        }
    }
}

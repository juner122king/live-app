package com.example.liveapp.feature.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import com.example.liveapp.core.model.Room

@Composable
fun PlayerScreen(
    room: Room?,
    onBackClick: () -> Unit,
    viewModel: PlayerViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(room?.id) {
        room?.let(viewModel::bindRoom)
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.releasePlayer() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "播放页")
        Text(text = state.room?.title ?: "未选择房间")
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            factory = { context ->
                PlayerView(context).also(viewModel::attachPlayerView)
            }
        )
        Text(text = state.room?.playUrl ?: "暂无播放地址")
        state.errorMessage?.let { Text(text = it) }
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("返回")
        }
    }
}

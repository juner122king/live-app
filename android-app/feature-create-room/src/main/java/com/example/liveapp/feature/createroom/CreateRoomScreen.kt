package com.example.liveapp.feature.createroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.liveapp.core.model.Room

@Composable
fun CreateRoomScreen(
    onBackClick: () -> Unit,
    onRoomCreated: (Room) -> Unit,
    viewModel: CreateRoomViewModel,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.room?.id) {
        state.room?.let(onRoomCreated)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "创建直播间")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("标题") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.coverUrl,
            onValueChange = viewModel::onCoverUrlChange,
            label = { Text("封面 URL") }
        )
        state.errorMessage?.let { Text(text = it) }
        Button(onClick = viewModel::submit, enabled = !state.isSubmitting) {
            Text(if (state.isSubmitting) "创建中..." else "创建并去开播")
        }
        Button(onClick = onBackClick) {
            Text("返回")
        }
    }
}

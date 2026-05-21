package com.example.liveapp.feature.publish

import android.Manifest
import android.content.pm.PackageManager
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.liveapp.core.model.Room

@Composable
fun PublishScreen(
    room: Room?,
    onBackClick: () -> Unit,
    viewModel: PublishViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var permissionsGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        permissionsGranted = result[Manifest.permission.CAMERA] == true && result[Manifest.permission.RECORD_AUDIO] == true
    }

    LaunchedEffect(room?.id) {
        room?.let(viewModel::bindRoom)
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.stopPreview() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "推流页")
        Text(text = state.room?.title ?: "未绑定房间")
        if (permissionsGranted) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                factory = { frameContext ->
                    FrameLayout(frameContext).also(viewModel::attachPreview)
                }
            )
        } else {
            Button(
                onClick = {
                    permissionLauncher.launch(
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("请求相机和麦克风权限")
            }
        }
        Text(text = if (state.isLive) "直播中" else "未开播")
        state.errorMessage?.let { Text(text = it) }
        Button(onClick = viewModel::startLive, modifier = Modifier.fillMaxWidth(), enabled = permissionsGranted) {
            Text("开始直播")
        }
        Button(onClick = viewModel::stopLive, modifier = Modifier.fillMaxWidth()) {
            Text("结束直播")
        }
        Button(onClick = viewModel::switchCamera, modifier = Modifier.fillMaxWidth()) {
            Text("切换摄像头")
        }
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("返回")
        }
    }
}

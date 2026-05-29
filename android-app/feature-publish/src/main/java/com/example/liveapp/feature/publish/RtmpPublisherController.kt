package com.example.liveapp.feature.publish

import android.content.Context
import android.util.Log
import com.pedro.common.ConnectChecker
import com.pedro.encoder.input.sources.video.Camera2Source
import com.pedro.library.generic.GenericStream
import com.pedro.library.view.OpenGlView

interface PublisherConnectionListener {
    fun onConnectionSuccess()
    fun onConnectionFailed(reason: String)
    fun onDisconnect()
}

class RtmpPublisherController(private val context: Context) : ConnectChecker {
    companion object {
        private const val TAG = "LivePublisher"
    }

    private val stream = GenericStream(context, this)
    var connectionListener: PublisherConnectionListener? = null

    fun attachPreview(openGlView: OpenGlView) {
        Log.d(TAG, "attachPreview")
        if (stream.isOnPreview) return
        stream.getGlInterface().autoHandleOrientation = true
        if (!stream.isStreaming) {
            val videoPrepared = stream.prepareVideo(1280, 720, 2500_000, 30, 0)
            if (!videoPrepared) error("视频编码器初始化失败")
            val audioPrepared = stream.prepareAudio(44100, true, 128_000)
            if (!audioPrepared) error("音频编码器初始化失败")
        }
        stream.startPreview(openGlView, true)
    }

    fun stopPreview() {
        Log.d(TAG, "stopPreview")
        if (stream.isOnPreview) {
            stream.stopPreview(true)
        }
    }

    fun startPublish(url: String) {
        Log.d(TAG, "startPublish: $url")
        if (!stream.isStreaming) {
            stream.startStream(url)
        }
    }

    fun stopPublish() {
        Log.d(TAG, "stopPublish")
        if (stream.isStreaming) {
            stream.stopStream()
        }
    }

    fun switchCamera() {
        Log.d(TAG, "switchCamera")
        val source = stream.videoSource
        if (source is Camera2Source) {
            source.switchCamera()
        }
    }

    // -- ConnectChecker --

    override fun onConnectionStarted(url: String) {
        Log.d(TAG, "onConnectionStarted: $url")
    }

    override fun onConnectionSuccess() {
        Log.d(TAG, "onConnectionSuccess")
        connectionListener?.onConnectionSuccess()
    }

    override fun onConnectionFailed(reason: String) {
        Log.e(TAG, "onConnectionFailed: $reason")
        if (stream.isStreaming) {
            stream.stopStream()
        }
        connectionListener?.onConnectionFailed(reason)
    }

    override fun onNewBitrate(bitrate: Long) {
        // no-op
    }

    override fun onDisconnect() {
        Log.d(TAG, "onDisconnect")
        connectionListener?.onDisconnect()
    }

    override fun onAuthError() {
        Log.e(TAG, "onAuthError")
    }

    override fun onAuthSuccess() {
        Log.d(TAG, "onAuthSuccess")
    }
}

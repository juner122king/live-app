package com.example.liveapp.feature.publish

import android.content.Context
import android.view.ViewGroup
import cn.nodemedia.NodePublisher

class NodeMediaPublisherController(context: Context) {
    private val publisher = NodePublisher(context, "")

    fun attachPreview(container: ViewGroup) {
        publisher.attachView(container)
        publisher.openCamera(0)
    }

    fun startPreview() = Unit

    fun stopPreview() {
        publisher.closeCamera()
    }

    fun startPublish(pushUrl: String) {
        publisher.start(pushUrl)
    }

    fun stopPublish() {
        publisher.stop()
    }

    fun switchCamera() {
        publisher.switchCamera()
    }
}


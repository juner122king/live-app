package com.example.liveapi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class StreamProperties(
    val rtmpBaseUrl: String,
    val hlsBaseUrl: String,
)

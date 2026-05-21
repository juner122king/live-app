package com.example.liveapi

import com.example.liveapi.config.StreamProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(StreamProperties::class)
class LiveApplication

fun main(args: Array<String>) {
    runApplication<LiveApplication>(*args)
}

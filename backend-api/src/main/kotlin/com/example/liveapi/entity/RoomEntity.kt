package com.example.liveapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "rooms")
data class RoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val title: String,
    @Column(name = "cover_url", nullable = false)
    val coverUrl: String,
    @Column(name = "stream_key", nullable = false, unique = true)
    val streamKey: String,
    @Column(name = "play_url", nullable = false)
    val playUrl: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: LiveStatus,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime,
)

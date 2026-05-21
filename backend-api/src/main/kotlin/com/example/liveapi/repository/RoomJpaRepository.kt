package com.example.liveapi.repository

import com.example.liveapi.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomJpaRepository : JpaRepository<RoomEntity, Long>

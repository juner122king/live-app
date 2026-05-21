package com.example.liveapi

import com.example.liveapi.config.StreamProperties
import com.example.liveapi.dto.CreateRoomRequest
import com.example.liveapi.entity.LiveStatus
import com.example.liveapi.entity.RoomEntity
import com.example.liveapi.repository.RoomJpaRepository
import com.example.liveapi.service.RoomService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.web.server.ResponseStatusException
import java.util.Optional

class RoomServiceTest {

    private val repository: RoomJpaRepository = mock()
    private val streamProperties = StreamProperties(
        rtmpBaseUrl = "rtmp://10.0.2.2:1935/live",
        hlsBaseUrl = "http://10.0.2.2:8080/hls"
    )

    @Test
    fun `createRoom returns offline room with derived push and play urls`() {
        val service = RoomService(repository, streamProperties)
        whenever(repository.save(any())).thenAnswer { invocation ->
            val room = invocation.arguments[0] as RoomEntity
            room.copy(id = 1L)
        }

        val room = service.createRoom(CreateRoomRequest(title = "Room 1", coverUrl = "https://example.com/1.jpg"))

        assertEquals(1L, room.id)
        assertEquals("Room 1", room.title)
        assertEquals("https://example.com/1.jpg", room.coverUrl)
        assertEquals(LiveStatus.OFFLINE, room.status)
        assertNotNull(room.streamKey)
        assertEquals("rtmp://10.0.2.2:1935/live/${room.streamKey}", room.pushUrl)
        assertEquals("http://10.0.2.2:8080/hls/${room.streamKey}.m3u8", room.playUrl)
    }

    @Test
    fun `getRoom throws not found when room does not exist`() {
        val service = RoomService(repository, streamProperties)
        whenever(repository.findById(99L)).thenReturn(Optional.empty())

        assertThrows(ResponseStatusException::class.java) {
            service.getRoom(99L)
        }
    }
}

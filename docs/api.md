# API

## POST /api/rooms
创建直播间。

### Request
```json
{
  "title": "My Live Room",
  "coverUrl": "https://example.com/cover.jpg"
}
```

### Response
```json
{
  "id": 1,
  "title": "My Live Room",
  "coverUrl": "https://example.com/cover.jpg",
  "streamKey": "room-1-key",
  "pushUrl": "rtmp://10.0.2.2:1935/live/room-1-key",
  "playUrl": "http://10.0.2.2:8080/hls/room-1-key.m3u8",
  "status": "OFFLINE"
}
```

## GET /api/rooms
获取房间列表。

## GET /api/rooms/{id}
获取房间详情。

## POST /api/rooms/{id}/start
标记房间开播。

## POST /api/rooms/{id}/stop
标记房间下播。

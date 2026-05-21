# Architecture

## Overall

- Android 单 App，内部拆分 feature/core 模块
- Spring Boot 管理房间与直播状态
- SRS 负责 RTMP ingest 与 HLS 切片
- nginx 统一转发 `/api` 与 `/hls`

## Data Flow

1. 创建直播间
2. 后端返回 `roomId`、`streamKey`、`pushUrl`、`playUrl`
3. 推流页开启 Camera Preview 并调用开播接口
4. NodeMediaClient 推 RTMP 到 SRS
5. SRS 生成 HLS
6. 列表页读取房间列表
7. 播放页使用 Media3 播放 HLS

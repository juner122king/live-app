# Run Local

## Prerequisites
- JDK 17+
- Android Studio
- Docker Desktop
- Android 真机或模拟器

## Service startup order
1. `docker compose -f infra/docker-compose.yml up -d`
2. `backend-api` 启动
3. Android App 运行

## Android network notes
- Android 模拟器访问宿主机请使用 `10.0.2.2`
- 真机需要将 `10.0.2.2` 改成宿主机局域网 IP

## SRS output check
启动推流后访问：
- `http://localhost:8080/hls/<streamKey>.m3u8`

## PostgreSQL defaults
- DB: `live_app`
- User: `live`
- Password: `live123`

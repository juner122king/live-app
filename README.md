# Live App MVP

一个可直接运行的 Android 直播 App MVP。

## 技术栈

### Android
- Kotlin
- Jetpack Compose
- MVVM
- Retrofit
- Coil
- AndroidX Media3
- NodeMediaClient Android SDK

### 服务端
- Spring Boot
- PostgreSQL
- SRS
- nginx
- Docker Compose

## 项目结构

```text
live-app/
├── android-app/      # 单 Android App，多模块
├── backend-api/      # Spring Boot API
├── infra/            # docker compose 与 nginx
├── srs-server/       # SRS 配置
└── docs/             # 文档
```

## 本地启动步骤

### 1. 启动基础设施

```bash
docker compose -f infra/docker-compose.yml up -d
```

启动后将包含：
- PostgreSQL: `localhost:5432`
- SRS RTMP: `localhost:1935`
- nginx HLS/API 网关: `http://localhost:8080`

### 2. 启动后端

```bash
cd backend-api
./gradlew bootRun
```

Windows:

```powershell
cd backend-api
.\gradlew.bat bootRun
```

默认 API：`http://10.0.2.2:8081/api`

### 3. 启动 Android App

用 Android Studio 打开 `android-app/`，同步 Gradle 后运行 `app` 模块。

推荐：
- 播放端可用模拟器
- 推流端建议真机验证相机与麦克风权限

## 主要功能
- 创建直播间
- 直播列表
- Camera Preview
- RTMP 推流
- 开播 / 下播
- HLS 播放
- ExoPlayer 播放

## 当前 MVP 约束
- 自动重连暂未实现
- live 状态由后端业务接口维护
- 默认使用本地网络地址联调

详细说明见：
- `docs/architecture.md`
- `docs/api.md`
- `docs/run-local.md`

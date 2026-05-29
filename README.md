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
- RootEncoder

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

当前默认真机联调 API：`http://192.168.112.150:8081/api`

当前项目已经默认按这台电脑的局域网地址配置真机联调：

```properties
liveApiBaseUrl=http://192.168.112.150:8081/api/
```

后端默认返回的推流/播放地址也已对应改为：

```text
rtmp://192.168.112.150:1935/live
http://192.168.112.150:8080/live
```

nginx 仍保留 `/hls` 作为兼容入口，旧房间的 `/hls/<streamKey>.m3u8` 播放地址会被转发到 SRS 的 `/live` HLS 文件。

如果这台电脑的 IP 之后变化，再把它们一起改掉即可。

### 3. 启动 Android App

用 Android Studio 打开 `android-app/`，同步 Gradle 后运行 `app` 模块。

推荐：
- 当前默认配置按真机联调准备
- 手机与电脑必须在同一局域网
- 推流端建议真机验证相机与麦克风权限
- 如无法访问，请检查 Windows 防火墙是否放行 8081 / 8080 / 1935

## 主要功能
- 创建直播间
- 直播列表
- Camera Preview
- RTMP 推流
- 开播 / 下播
- HLS 播放
- ExoPlayer 播放

## MVP 闭环

当前项目已经形成可运行的直播 MVP 链路：

```text
创建直播间 -> 获取 pushUrl/playUrl -> 真机 Camera Preview -> RTMP 推流到 SRS
-> SRS 生成 HLS -> 列表进入播放页 -> Media3 播放 -> 下播更新状态
```

关键模块职责：
- `android-app/`: Android 多模块 App，包含房间创建、直播列表、推流页和播放页。
- `backend-api/`: Spring Boot API，管理直播间、streamKey、推流地址、播放地址和直播状态。
- `srs-server/`: SRS 配置，负责 RTMP ingest 和 HLS 切片输出。
- `infra/`: PostgreSQL、SRS、nginx 的 Docker Compose 与网关配置。

当前运行基线：
- API 默认真机联调地址为 `http://192.168.112.150:8081/api/`。
- RTMP 推流地址格式为 `rtmp://192.168.112.150:1935/live/<streamKey>`。
- HLS 主播放地址格式为 `http://192.168.112.150:8080/live/<streamKey>.m3u8`。
- `/hls/<streamKey>.m3u8` 仅作为历史兼容路径保留。

## 当前 MVP 约束
- 自动重连暂未实现
- live 状态由后端业务接口维护，暂未接入 SRS 回调
- 默认使用本地网络地址联调
- 暂无用户体系、鉴权、房间归属和推流权限校验
- 推流失败、网络断开、后台切换、权限拒绝等异常路径仍需加强

详细说明见：
- `docs/architecture.md`
- `docs/api.md`
- `docs/run-local.md`

## 后续迭代储备

建议优先迭代：
- 稳定性：推流断线重连、播放重试、前后台生命周期、下播兜底。
- 状态真实性：接入 SRS HTTP callback 或服务端轮询，避免仅靠客户端按钮维护 LIVE 状态。
- 产品体验：直播间封面校验、空态/错误态、刷新、当前直播过滤、播放加载状态。
- 安全能力：用户登录、房间归属、推流密钥校验、防止任意客户端推同一个 streamKey。
- 工程化：同步 docs、补 Android ViewModel 测试、补 API 集成测试、区分 dev/staging 配置。
- 直播体验：静音、前后摄像头状态、码率配置、横竖屏适配、播放延迟提示。

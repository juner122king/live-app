# Repository Guidelines

## Project Structure & Module Organization

This repository contains a live-streaming MVP split into Android, backend, and infrastructure projects.

- `android-app/`: multi-module Android app using Kotlin, Jetpack Compose, MVVM, Retrofit, Media3, and NodeMediaClient. Feature modules live under `feature-*`; shared code lives in `core-*`; the launcher app is `app`.
- `backend-api/`: Spring Boot Kotlin API. Source code is in `src/main/kotlin`, Flyway migrations in `src/main/resources/db/migration`, and tests in `src/test/kotlin`.
- `infra/`: Docker Compose and nginx configuration.
- `srs-server/`: SRS streaming server configuration.
- `docs/`: architecture, API, and local run notes.

## Build, Test, and Development Commands

- `docker compose -f infra/docker-compose.yml up -d`: starts PostgreSQL, SRS, and nginx.
- `cd backend-api && ./gradlew bootRun`: runs the Spring Boot API.
- `cd backend-api && .\gradlew.bat bootRun`: same command for Windows PowerShell.
- `cd backend-api && ./gradlew test`: runs backend JUnit 5 tests.
- `cd android-app && ./gradlew assembleDebug`: builds the Android debug APK.
- `cd android-app && ./gradlew test`: runs Android unit tests where present.

Use Android Studio to open `android-app/`, sync Gradle, and run `app`.

## Coding Style & Naming Conventions

Use Kotlin and Java 17 conventions. Keep packages under `com.example.liveapp` for Android and `com.example.liveapi` for backend code. Prefer existing suffixes: `Screen`, `ViewModel`, `Repository`, `Controller`, `Service`, `Entity`, `Dto`, and `Response`. Compose UI functions use PascalCase; variables, functions, and properties use camelCase.

Keep feature code inside the matching `feature-*` module and reusable code inside `core-*`.

## Testing Guidelines

Backend tests use JUnit 5 and Mockito Kotlin. Name tests after behavior, as in ``createRoom returns offline room with derived push and play urls``. Add tests for service logic, validation, and API behavior changes. Android modules currently have minimal tests; add focused ViewModel and repository tests when changing logic.

## Commit & Pull Request Guidelines

History uses Conventional Commit style, for example `feat: scaffold Android live app MVP`. Continue with `feat:`, `fix:`, `test:`, `docs:`, and `refactor:`.

Pull requests should include a summary, validation commands, linked issues when applicable, and screenshots or recordings for Android UI changes. Mention network changes, especially `liveApiBaseUrl`, RTMP, HLS, or ports.

## Security & Configuration Tips

Local defaults are in `README.md` and `docs/run-local.md`. Do not commit real credentials, production endpoints, signing keys, or machine-specific IP changes unless intentional and documented.

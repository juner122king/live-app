pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "live-app"
include(":app")
include(":core-model")
include(":core-network")
include(":core-ui")
include(":feature-create-room")
include(":feature-publish")
include(":feature-room-list")
include(":feature-player")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.liveapp.core.network"
    compileSdk = 35
    defaultConfig { minSdk = 26 }
    buildFeatures { buildConfig = true }
    buildTypes {
        release {
            buildConfigField(
                "String",
                "API_BASE_URL",
                "\"${providers.gradleProperty("liveApiBaseUrl").get()}\""
            )
        }
        debug {
            buildConfigField(
                "String",
                "API_BASE_URL",
                "\"${providers.gradleProperty("liveApiBaseUrl").get()}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":core-model"))
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
}

package com.example.liveapp

import android.app.Application

class LiveApplication : Application() {
    val appContainer by lazy { AppContainer() }
}

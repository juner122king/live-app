package com.example.liveapp.core.network.repository

import com.example.liveapp.core.network.BuildConfig
import com.example.liveapp.core.network.api.LiveApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val liveApiService: LiveApiService = retrofit.create(LiveApiService::class.java)

    val roomRepository: RoomRepository = RoomRepositoryImpl(liveApiService)
}

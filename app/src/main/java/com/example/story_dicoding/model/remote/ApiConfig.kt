package com.example.story_dicoding.model.remote

import com.example.story_dicoding.BuildConfig
import com.example.story_dicoding.model.preferences.SettingPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{

        var BASE_URL = BuildConfig.BASE_URL
        fun getApiService(pref: SettingPreferences): ApiService {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val token = runBlocking { pref.getToken().first() }
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(requestHeaders)
            }
            val loggingInterceptor =
                if(BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
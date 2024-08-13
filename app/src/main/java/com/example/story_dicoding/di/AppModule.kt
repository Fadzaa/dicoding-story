package com.example.story_dicoding.di

import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.preferences.dataStore
import com.example.story_dicoding.model.remote.ApiConfig
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.viewmodel.AddStoryViewModel
import com.example.story_dicoding.viewmodel.AuthViewModel
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiService> {
        ApiConfig.getApiService(SettingPreferences.getInstance(androidApplication().dataStore))
    }

    viewModel { AuthViewModel(get(), SettingPreferences.getInstance(androidApplication().dataStore)) }
    viewModel { StoryViewModel(get()) }
    viewModel { AddStoryViewModel(get()) }

}
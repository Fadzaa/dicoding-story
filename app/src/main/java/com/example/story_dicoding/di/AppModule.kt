package com.example.story_dicoding.di

import com.example.story_dicoding.model.remote.ApiConfig
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.viewmodel.AuthViewModel
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiService> {
        ApiConfig.getApiService()
    }

    viewModel { AuthViewModel(get()) }
    viewModel { StoryViewModel(get()) }
//    viewModel { ThemeViewModel(SettingPreferences.getInstance(androidApplication().dataStore)) }
//    factory { (username: String) -> RepositoryViewModel(username, get()) }
//    factory { (username: String) -> DetailViewModel(username, get()) }
//    factory { FavouriteViewModel(androidApplication()) }

}
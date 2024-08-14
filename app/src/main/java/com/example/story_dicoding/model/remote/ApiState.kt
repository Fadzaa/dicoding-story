package com.example.story_dicoding.model.remote

data class ApiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null,
)

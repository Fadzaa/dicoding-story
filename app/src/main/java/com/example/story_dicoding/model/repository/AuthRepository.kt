package com.example.story_dicoding.model.repository

import com.example.story_dicoding.helper.wrapEspressoIdlingResource
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.LoginResponse
import com.example.story_dicoding.model.remote.response.RegisterResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {
    suspend fun register(name: String, email: String, password: String) : Response<RegisterResponse> = apiService.register(name, email, password)

    suspend fun login(email: String, password: String) : Response<LoginResponse> = wrapEspressoIdlingResource { apiService.login(email, password) }

}
package com.example.story_dicoding.viewmodel

import androidx.lifecycle.ViewModel
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.repository.AuthRepository

class AuthViewModel(apiService: ApiService): ViewModel() {
    private val authRepository = AuthRepository(apiService)

    fun registerUser(email: String, password: String, name: String) {
        authRepository.registerUser(email, password, name)
    }

    fun loginUser(email: String, password: String) {
        authRepository.loginUser(email, password)
    }


}
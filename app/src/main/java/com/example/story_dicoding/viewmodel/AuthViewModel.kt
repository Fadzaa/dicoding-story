package com.example.story_dicoding.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.LoginResponse
import com.example.story_dicoding.model.remote.response.RegisterResponse
import com.example.story_dicoding.model.repository.AuthRepository
import com.example.story_dicoding.view.activity.ListStoryActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(apiService: ApiService, private val pref: SettingPreferences): ViewModel() {
    private val authRepository = AuthRepository(apiService)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(email: String, password: String, name: String): LiveData<RegisterResponse> {
        _isLoading.value = true
        val registerResponse = authRepository.registerUser(email, password, name)

        registerResponse.observeForever {
            _isLoading.value = false
        }

        return registerResponse
    }

    fun loginUser(email: String, password: String): LiveData<LoginResponse> {
        _isLoading.value = true
        val loginResponse = authRepository.loginUser(email, password)

        loginResponse.observeForever {  response ->
            viewModelScope.launch {
                pref.saveToken(response.loginResult.token)
            }
            _isLoading.value = false
        }

        return loginResponse
    }
    
    fun checkUserTokenPreferences(activity: Activity) {
        viewModelScope.launch {
            val token = pref.getToken().first()

            if (token != null) {
                val intent = Intent(activity, ListStoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity.startActivity(intent)
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            pref.removeToken()
        }
    }

}
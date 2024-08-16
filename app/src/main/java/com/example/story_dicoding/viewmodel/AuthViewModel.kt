package com.example.story_dicoding.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.repository.AuthRepository
import com.example.story_dicoding.view.activity.ListStoryActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class AuthViewModel(apiService: ApiService, private val pref: SettingPreferences): ViewModel() {
    private val authRepository = AuthRepository(apiService)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun registerUser(name: String, email: String, password: String, activity: Context) = viewModelScope.launch {
        _isLoading.value = true

        val registerResponse = authRepository.register(name, email, password)

        if (registerResponse.isSuccessful) {
            Toast.makeText(activity, "Register Success", Toast.LENGTH_SHORT).show()
            activity.startActivity(Intent(activity, ListStoryActivity::class.java))
            _isLoading.value = false
        } else {
            errorResponse(registerResponse)
        }


    }

    fun loginUser(email: String, password: String, activity: Context) = viewModelScope.launch {
        _isLoading.value = true
        val loginResponse = authRepository.login(email, password)

        if (loginResponse.isSuccessful) {
            val token = loginResponse.body()?.loginResult?.token
            pref.saveToken(token!!)

            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, ListStoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            _isLoading.value = false

        } else {
            errorResponse(loginResponse)
        }
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

    private fun errorResponse(response: Response<*>) {
        val errorBody = response.errorBody()?.string()
        val errorMessage = JSONObject(errorBody.toString()).getString("message")
        _errorMessage.value = errorMessage
        _isLoading.value = false
    }

}
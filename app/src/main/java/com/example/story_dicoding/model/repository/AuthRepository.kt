package com.example.story_dicoding.model.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.LoginResponse
import com.example.story_dicoding.model.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    fun registerUser(email: String, password: String, name: String) : LiveData<RegisterResponse> {
        val registerResponse = MutableLiveData<RegisterResponse>()

        apiService.register(
            name = name,
            email = email,
            password = password
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body().toString()}")
                    response.body()?.let {
                        registerResponse.value = it
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return registerResponse
    }

    fun loginUser(email: String, password: String) : LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()

        apiService.login(
            email = email,
            password = password
        ).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body().toString()}")
                    response.body()?.let {
                        loginResponse.value = it
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return loginResponse
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}
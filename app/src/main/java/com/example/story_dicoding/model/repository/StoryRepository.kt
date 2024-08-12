package com.example.story_dicoding.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(private val apiService: ApiService) {

    fun getAllStory(): LiveData<AllStoryResponse> {
        val allStoryResponse = MutableLiveData<AllStoryResponse>()

        apiService.getAllStory(1, 1, 0).enqueue(
            object : Callback<AllStoryResponse> {
                override fun onResponse(
                    call: Call<AllStoryResponse>,
                    response: Response<AllStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            allStoryResponse.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return allStoryResponse
    }

    fun getStoryById(id: String): LiveData<DetailStoryResponse> {
        val detailStoryResponse = MutableLiveData<DetailStoryResponse>()

        apiService.getDetailStory(id).enqueue(
            object : Callback<DetailStoryResponse> {
                override fun onResponse(
                    call: Call<DetailStoryResponse>,
                    response: Response<DetailStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            detailStoryResponse.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return detailStoryResponse
    }

    companion object {
        private const val TAG = "StoryRepository"
    }
}
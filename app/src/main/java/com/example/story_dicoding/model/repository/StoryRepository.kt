package com.example.story_dicoding.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.AddStoryResponse
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoryRepository(private val apiService: ApiService) {

    fun getAllStory(): LiveData<AllStoryResponse> {
        val allStoryResponse = MutableLiveData<AllStoryResponse>()

        apiService.getAllStory(1, 10, 0).enqueue(
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

    fun addStoryGuest(
        file: File,
        description: String,
        lat: Float,
        lon: Float,
    ): LiveData<AddStoryResponse> {
        val addStoryResponse = MutableLiveData<AddStoryResponse>()
        val descriptionReq = description.toRequestBody("text/plain".toMediaType())
        val latReq = lat.toString().toRequestBody("text/plain".toMediaType())
        val lonReq = lon.toString().toRequestBody("text/plain".toMediaType())

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)

        apiService.addStory(imageMultipart,descriptionReq, latReq, lonReq).enqueue(
            object : Callback<AddStoryResponse> {
                override fun onResponse(
                    call: Call<AddStoryResponse>,
                    response: Response<AddStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            addStoryResponse.value = it
                        }
                        Log.d(TAG, "onResponse: ${response.body().toString()}")
                    }
                }

                override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            }
        )

        return addStoryResponse
    }


    companion object {
        private const val TAG = "StoryRepository"
    }
}
package com.example.story_dicoding.model.repository

import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.AddStoryResponse
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class StoryRepository(private val apiService: ApiService) {

    suspend fun getAllStory(page: Int, size: Int, location: Int): Response<AllStoryResponse> = apiService.getAllStory(page, size, location)


    suspend fun getStoryById(id: String): Response<DetailStoryResponse> = apiService.getDetailStory(id)

    suspend fun addStory(
        file: File,
        description: String,
        lat: Float,
        lon: Float,
    ): Response<AddStoryResponse> {
        val descriptionReq = description.toRequestBody("text/plain".toMediaType())
        val latReq = lat.toString().toRequestBody("text/plain".toMediaType())
        val lonReq = lon.toString().toRequestBody("text/plain".toMediaType())

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestImageFile)

        return apiService.addStory(imageMultipart, descriptionReq, latReq, lonReq)
    }

}
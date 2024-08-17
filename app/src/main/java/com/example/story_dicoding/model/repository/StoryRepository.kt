package com.example.story_dicoding.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.story_dicoding.model.data.StoryRemoteMediator
import com.example.story_dicoding.model.local.StoryDatabase
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.AddStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.Story
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class StoryRepository(private val apiService: ApiService, private val storyDatabase: StoryDatabase) {

    fun getAllStory(): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
            ),

            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),

            pagingSourceFactory = {

                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }


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
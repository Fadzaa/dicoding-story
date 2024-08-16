package com.example.story_dicoding.model.remote

import com.example.story_dicoding.model.remote.response.AddStoryResponse
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.LoginResponse
import com.example.story_dicoding.model.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") long: RequestBody,
    ): Response<AddStoryResponse>

    @GET("stories")
    suspend fun getAllStory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 0
    ): Response<AllStoryResponse>

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String,
    ): Response<DetailStoryResponse>

}
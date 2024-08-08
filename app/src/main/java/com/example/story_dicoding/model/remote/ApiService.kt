package com.example.story_dicoding.model.remote

import com.example.story_dicoding.model.remote.response.AddStoryResponse
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.LoginResponse
import com.example.story_dicoding.model.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("stories")
    fun addStory(
        @Field("description") description: String,
        @Field("photo") photo: String,
        @Field("lat") lat: Float,
        @Field("lon") lon: Float,

    ): Call<AddStoryResponse>

    @FormUrlEncoded
    @POST("stories/guest")
    fun addStoryGuest(
        @Field("description") description: String,
        @Field("photo") photo: String,
        @Field("lat") lat: Float,
        @Field("lon") lon: Float,

    ): Call<AddStoryResponse>

    @GET("stories")
    fun getAllStory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 0
    ): Call<AllStoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: Int,
    ): Call<DetailStoryResponse>




}
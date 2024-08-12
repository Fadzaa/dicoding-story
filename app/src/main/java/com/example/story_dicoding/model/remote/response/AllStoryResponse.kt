package com.example.story_dicoding.model.remote.response


import com.google.gson.annotations.SerializedName

data class AllStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<Story>,
    @SerializedName("message")
    val message: String
)
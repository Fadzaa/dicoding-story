package com.example.story_dicoding.model.remote.response

import com.google.gson.annotations.SerializedName

data class DetailStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("story")
    val story: Story
)
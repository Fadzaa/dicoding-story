package com.example.story_dicoding.model.remote.response

data class DetailStoryResponse(
    val error: Boolean,
    val message: String,
    val story: Story
)
package com.example.story_dicoding.model.remote.response

data class AllStoryResponse(
    val error: Boolean,
    val listStory: List<Story>,
    val message: String
)
package com.example.story_dicoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.model.repository.StoryRepository


class StoryViewModel(apiService: ApiService): ViewModel() {
    private val storyRepository = StoryRepository(apiService)

    private val _allStory = MutableLiveData<AllStoryResponse>()
    val allStory: LiveData<AllStoryResponse> = _allStory

    private val _story = MutableLiveData<DetailStoryResponse>()
    val story: LiveData<DetailStoryResponse> = _story

    init {
        getAllStory()
        getStoryById("1")
    }

    private fun getAllStory() {
        storyRepository.getAllStory().observeForever {
            _allStory.value = it
        }
    }

    private fun getStoryById(id: String) {
        storyRepository.getStoryById(id).observeForever {
            _story.value = it
        }
    }


}
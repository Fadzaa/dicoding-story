package com.example.story_dicoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.model.repository.StoryRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response


class StoryViewModel(apiService: ApiService): ViewModel() {
    private val storyRepository = StoryRepository(apiService)

    private val _allStory = MutableLiveData<List<Story>>()
    val allStory: LiveData<List<Story>> = _allStory

    private val _story = MutableLiveData<DetailStoryResponse>()
    val story: LiveData<DetailStoryResponse> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    init {
        getAllStory()
    }

    private fun getAllStory() = viewModelScope.launch {
        _isLoading.value = true

        storyRepository.getAllStory(1, 10, 0).let { response ->
            if (response.isSuccessful) {
                _allStory.value = response.body()?.listStory
                _isLoading.value = false
            } else {
                errorResponse(response)
            }

        }

    }

    fun getStoryById(id: String) = viewModelScope.launch {
        _isLoading.value = true

        storyRepository.getStoryById(id).let { response ->
            if (response.isSuccessful) {
                _story.value = response.body()
                _isLoading.value = false
            } else {
                _errorMessage.value = response.message()
                _isLoading.value = false
            }
        }
    }

    private fun errorResponse(response: Response<*>) {
        val errorBody = response.errorBody()?.string()
        val errorMessage = JSONObject(errorBody.toString()).getString("message")
        _errorMessage.value = errorMessage
        _isLoading.value = false
    }
}
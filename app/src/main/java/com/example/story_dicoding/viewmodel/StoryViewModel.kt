package com.example.story_dicoding.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.DetailStoryResponse
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.model.repository.StoryRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response


class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    val allStory: LiveData<PagingData<Story>> = storyRepository.getAllStory().cachedIn(viewModelScope)

    private val _allStoryLocation = MutableLiveData<List<Story>>()
    val allStoryLocation: LiveData<List<Story>> get() {
        if (_allStoryLocation.value == null) getAllStoryLocation()
        return _allStoryLocation
    }


    private val _story = MutableLiveData<DetailStoryResponse>()
    val story: LiveData<DetailStoryResponse> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isDataEmpty = MutableLiveData<Boolean>()
    val isDataEmpty: LiveData<Boolean> = _isDataEmpty

    private fun getAllStoryLocation() = viewModelScope.launch {
        _isLoading.value = true

        storyRepository.getAllStoryLocation().let { response ->
            if (response.isSuccessful) {
                _allStoryLocation.value = response.body()?.listStory
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

                errorResponse(response)
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
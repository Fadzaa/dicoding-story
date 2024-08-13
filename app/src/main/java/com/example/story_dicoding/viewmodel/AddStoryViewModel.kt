package com.example.story_dicoding.viewmodel

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.story_dicoding.helper.createCustomTempFile
import com.example.story_dicoding.helper.getImageUri
import com.example.story_dicoding.helper.reduceFileImage
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.repository.StoryRepository
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddStoryViewModel(apiService: ApiService): ViewModel() {
    private val storyRepository = StoryRepository(apiService)

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun getCameraImageUri(context: Activity) {
        _currentImageUri.value = getImageUri(context)
    }



    fun addStory(description: String, lat: Float, lon: Float, context: Context) {
        _isLoading.value = true
        val fileImage = uriToFile(currentImageUri.value!!, context).reduceFileImage()
        currentImageUri.observeForever {
            storyRepository.addStoryGuest(fileImage, description, lat, lon)
            _isLoading.value = false
        }
    }

    private fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }

}
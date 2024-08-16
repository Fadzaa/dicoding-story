package com.example.story_dicoding.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.story_dicoding.helper.createCustomTempFile
import com.example.story_dicoding.helper.getImageUri
import com.example.story_dicoding.helper.reduceFileImage
import com.example.story_dicoding.model.remote.ApiService
import com.example.story_dicoding.model.repository.StoryRepository
import com.example.story_dicoding.view.activity.MainActivity
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AddStoryViewModel(apiService: ApiService): ViewModel() {
    private val storyRepository = StoryRepository(apiService)

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun updateCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun getCameraImageUri(context: Activity) {
        _currentImageUri.value = getImageUri(context)
    }



    fun addStory(description: String, lat: Float, lon: Float, activity: Activity) = viewModelScope.launch {
        _isLoading.value = true
        val fileImage = currentImageUri.value?.let { uri ->
            uriToFile(uri, activity).reduceFileImage()
        }

        if (fileImage != null) {
            val response = storyRepository.addStory(fileImage, description, lat, lon)

            if (response.isSuccessful) {
                Toast.makeText(activity, "Story Uploaded", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity.startActivity(intent)
                activity.finish()

                _isLoading.value = false
            } else {
                errorResponse(response)
            }
        } else {
            _errorMessage.value = "No image selected"
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

    private fun errorResponse(response: Response<*>) {
        val errorBody = response.errorBody()?.string()
        val errorMessage = JSONObject(errorBody.toString()).getString("message")
        _errorMessage.value = errorMessage
        _isLoading.value = false
    }

}
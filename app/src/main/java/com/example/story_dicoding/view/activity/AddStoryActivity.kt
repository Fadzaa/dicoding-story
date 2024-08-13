package com.example.story_dicoding.view.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.story_dicoding.databinding.ActivityAddStoryBinding
import com.example.story_dicoding.helper.getImageUri
import com.example.story_dicoding.viewmodel.AddStoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    private val addStoryViewModel: AddStoryViewModel by viewModel<AddStoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }

        binding.btnUpload.setOnClickListener {
            addStoryViewModel.addStory(
                "Test Upload Story",
                0.3f,
                0.7f,
                this
            )
        }

    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

        if (uri != null) {
            Log.d("Photo Picker",  "Media selected: $uri") 

            addStoryViewModel.updateCurrentImageUri(uri)
            showImage()

        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        addStoryViewModel.getCameraImageUri(this)
        launcherIntentCamera.launch(addStoryViewModel.currentImageUri.value!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            addStoryViewModel.updateCurrentImageUri(null)
        }
    }

    private fun showImage() {
        addStoryViewModel.currentImageUri.observe(this) {
            Log.d("Photo Picker", "Current Image Uri: $it")
            binding.ivAddStory.setImageURI(it)
        }
    }




}
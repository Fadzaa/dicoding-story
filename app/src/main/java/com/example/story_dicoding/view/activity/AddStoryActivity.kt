package com.example.story_dicoding.view.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.story_dicoding.databinding.ActivityAddStoryBinding
import com.example.story_dicoding.helper.getImageUri
import com.example.story_dicoding.helper.setLoading
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
            ).observe(this) {
                it?.let {
                    Toast.makeText(this, "Story Uploaded", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }


        }

        addStoryViewModel.isLoading.observe(this) {
            binding.progressBarAddStory.setLoading(it)
        }

        playAnimation()
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

    private fun playAnimation() {
        val back = ObjectAnimator.ofFloat(binding.ivBack, View.ALPHA, 1f).setDuration(100)
        val header = ObjectAnimator.ofFloat(binding.headerAddStory, View.ALPHA, 1f).setDuration(100)
        val labelImage = ObjectAnimator.ofFloat(binding.labelImageStory, View.ALPHA, 1f).setDuration(100)
        val imageStory = ObjectAnimator.ofFloat(binding.ivAddStory, View.ALPHA, 1f).setDuration(100)
        val gallery = ObjectAnimator.ofFloat(binding.btnGallery, View.ALPHA, 1f).setDuration(100)
        val camera = ObjectAnimator.ofFloat(binding.btnCamera, View.ALPHA, 1f).setDuration(100)
        val labelDescription = ObjectAnimator.ofFloat(binding.labelDescriptionStory, View.ALPHA, 1f).setDuration(100)
        val description = ObjectAnimator.ofFloat(binding.etDescription, View.ALPHA, 1f).setDuration(100)
        val upload = ObjectAnimator.ofFloat(binding.btnUpload, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                back,
                header,
                labelImage,
                imageStory,
                gallery,
                camera,
                labelDescription,
                description,
                upload
            )
            startDelay = 100
        }.start()
    }
}
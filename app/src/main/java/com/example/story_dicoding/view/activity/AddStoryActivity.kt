package com.example.story_dicoding.view.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.story_dicoding.databinding.ActivityAddStoryBinding
import com.example.story_dicoding.helper.setLoading
import com.example.story_dicoding.viewmodel.AddStoryViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val addStoryViewModel: AddStoryViewModel by viewModel<AddStoryViewModel>()
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }

        binding.swCurrentLocation.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLocation()
            } else {
                latitude = 0.0
                longitude = 0.0
                Log.d("Location", "Latitude: $latitude, Longitude: $longitude")
            }

        }

        addStoryViewModel.isLoading.observe(this) {
            binding.progressBarAddStory.setLoading(it)
        }

        binding.btnUpload.setOnClickListener {
            addStoryViewModel.addStory(
                binding.etDescription.text.toString(),
                latitude,
                longitude,
                this
            )

        }

        addStoryViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        playAnimation()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLocation()
                }
                else -> {
                    Toast.makeText(
                        this,
                        "Permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }
            }.addOnFailureListener { exception ->
                Log.d("LocationError", "Failed to get location: $exception")
            }


        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
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
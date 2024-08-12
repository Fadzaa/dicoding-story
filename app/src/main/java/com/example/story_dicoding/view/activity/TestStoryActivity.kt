package com.example.story_dicoding.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.story_dicoding.R
import com.example.story_dicoding.databinding.ActivityTestStoryBinding
import com.example.story_dicoding.viewmodel.AuthViewModel
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestStoryBinding

    private val storyViewModel: StoryViewModel by viewModel<StoryViewModel>()

    val authViewModel: AuthViewModel by viewModel<AuthViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTestStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storyViewModel.allStory.observe(this) {
            binding.testAllStoryResponse.text = it.toString()
        }




        binding.btnLogout.setOnClickListener {
            authViewModel.logoutUser()
        }


    }
}
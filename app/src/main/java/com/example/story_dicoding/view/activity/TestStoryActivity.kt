package com.example.story_dicoding.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story_dicoding.R
import com.example.story_dicoding.databinding.ActivityTestStoryBinding
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.view.adapter.ListStoryAdapter
import com.example.story_dicoding.viewmodel.AuthViewModel
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestStoryBinding
    private lateinit var listStoryAdapter: ListStoryAdapter

    private val storyViewModel: StoryViewModel by viewModel<StoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTestStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        storyViewModel.isLoading.observe(this) {
            setLoading(it)
        }

        storyViewModel.allStory.observe(this) {
            bindRecyclerView(it)
        }

    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    private fun bindRecyclerView(listStory: List<Story>) {
        with(binding) {
            rvListStory.setHasFixedSize(true)
            rvListStory.layoutManager = LinearLayoutManager(this@TestStoryActivity)
            rvListStory.adapter = ListStoryAdapter(listStory)
        }
    }
}
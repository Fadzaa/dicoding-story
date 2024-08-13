package com.example.story_dicoding.view.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story_dicoding.databinding.ActivityListStoryBinding
import com.example.story_dicoding.helper.setLoading
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.view.adapter.ListStoryAdapter
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var listStoryAdapter: ListStoryAdapter

    private val storyViewModel: StoryViewModel by viewModel<StoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        storyViewModel.isLoading.observe(this) {
            binding.progressBarHome.setLoading(it)
        }

        storyViewModel.allStory.observe(this) {
            bindRecyclerView(it)
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.btnSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

    }

    private fun bindRecyclerView(listStory: List<Story>) {
        with(binding) {
            rvListStory.setHasFixedSize(true)
            rvListStory.layoutManager = LinearLayoutManager(this@ListStoryActivity)
            rvListStory.adapter = ListStoryAdapter(listStory)
        }
    }
}
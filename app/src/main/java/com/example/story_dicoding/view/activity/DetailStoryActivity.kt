package com.example.story_dicoding.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.story_dicoding.databinding.ActivityDetailStoryBinding
import com.example.story_dicoding.helper.setLoading
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    private val storyViewModel: StoryViewModel by viewModel<StoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val story = intent.getSerializableExtra(EXTRA_STORY) as Story

        storyViewModel.getStoryById(story.id)

        storyViewModel.story.observe(this) {
            with(binding) {
                Glide.with(this@DetailStoryActivity)
                    .load(it.story.photoUrl)
                    .into(ivDetailStory)

                tvDetailUsername.text = it.story.name
                tvDetailDescription.text = it.story.description
            }
        }

        storyViewModel.isLoading.observe(this) {
            binding.progressBarDetail.setLoading(it)
        }




    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}
package com.example.story_dicoding.view.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        storyViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        playAnimation()

    }

    private fun playAnimation() {
        val back = ObjectAnimator.ofFloat(binding.ivBack, View.ALPHA, 1f).setDuration(100)
        val header = ObjectAnimator.ofFloat(binding.headerDetailStory, View.ALPHA, 1f).setDuration(100)
        val image = ObjectAnimator.ofFloat(binding.ivDetailStory, View.ALPHA, 1f).setDuration(100)
        val name = ObjectAnimator.ofFloat(binding.tvDetailUsername, View.ALPHA, 1f).setDuration(100)
        val description = ObjectAnimator.ofFloat(binding.tvDetailDescription, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                back,
                header,
                image,
                name,
                description
            )
            startDelay = 100
        }.start()
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}
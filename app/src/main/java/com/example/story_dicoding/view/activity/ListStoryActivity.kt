package com.example.story_dicoding.view.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story_dicoding.databinding.ActivityListStoryBinding
import com.example.story_dicoding.helper.setLoading
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.view.adapter.ListStoryAdapter
import com.example.story_dicoding.viewmodel.AuthViewModel
import com.example.story_dicoding.viewmodel.StoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListStoryBinding
    private lateinit var listStoryAdapter: ListStoryAdapter

    private val storyViewModel: StoryViewModel by viewModel<StoryViewModel>()
    private val authViewModel: AuthViewModel by viewModel<AuthViewModel>()

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

        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.ivSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.ivLogout.setOnClickListener {
            authViewModel.logoutUser()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        playAnimation()
    }

    private fun bindRecyclerView(listStory: List<Story>) {
        with(binding) {
            rvListStory.setHasFixedSize(true)
            rvListStory.layoutManager = LinearLayoutManager(this@ListStoryActivity)
            listStoryAdapter = ListStoryAdapter(listStory)
            rvListStory.adapter = listStoryAdapter
        }
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.ivLogout, View.ALPHA, 1f).setDuration(100)
        val setting = ObjectAnimator.ofFloat(binding.ivSetting, View.ALPHA, 1f).setDuration(100)
        val listStory = ObjectAnimator.ofFloat(binding.rvListStory, View.ALPHA, 1f).setDuration(100)
        val addStory = ObjectAnimator.ofFloat(binding.btnAddStory, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                logo,
                logout,
                setting,
                listStory,
                addStory
            )
            startDelay = 100
        }.start()
    }
}
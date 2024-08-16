package com.example.story_dicoding.view.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.story_dicoding.databinding.ActivityListStoryBinding
import com.example.story_dicoding.view.adapter.ListStoryAdapter
import com.example.story_dicoding.view.adapter.LoadingStateAdapter
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

        storyViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        storyViewModel.isDataEmpty.observe(this) {
            binding.tvEmptyData.visibility = if (it) View.VISIBLE else View.GONE
        }

        bindRecyclerView()

        bindBtnNavigation()
        playAnimation()
    }

    private fun bindRecyclerView() {
        with(binding) {
            rvListStory.setHasFixedSize(true)
            rvListStory.layoutManager = LinearLayoutManager(this@ListStoryActivity)
            listStoryAdapter = ListStoryAdapter()
            rvListStory.adapter = listStoryAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    listStoryAdapter.retry()
                }
            )

            storyViewModel.allStory.observe(this@ListStoryActivity) {
                listStoryAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(100)
        val logout = ObjectAnimator.ofFloat(binding.ivLogout, View.ALPHA, 1f).setDuration(100)
        val setting = ObjectAnimator.ofFloat(binding.ivSetting, View.ALPHA, 1f).setDuration(100)
        val map = ObjectAnimator.ofFloat(binding.ivMap, View.ALPHA, 1f).setDuration(100)
        val listStory = ObjectAnimator.ofFloat(binding.rvListStory, View.ALPHA, 1f).setDuration(100)
        val addStory = ObjectAnimator.ofFloat(binding.btnAddStory, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                logo,
                map,
                logout,
                setting,
                listStory,
                addStory
            )
            startDelay = 100
        }.start()
    }

    private fun bindBtnNavigation() {
        binding.btnAddStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }

        binding.ivSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.ivMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.ivLogout.setOnClickListener {
            authViewModel.logoutUser()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
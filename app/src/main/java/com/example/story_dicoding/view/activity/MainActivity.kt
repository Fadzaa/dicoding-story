package com.example.story_dicoding.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.story_dicoding.R
import com.example.story_dicoding.databinding.ActivityMainBinding
import com.example.story_dicoding.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            authViewModel.loginUser("fattah77@gmail.com", "fattah123")
        }

        binding.btnRegister.setOnClickListener {
            authViewModel.registerUser("test@gmail.com", "test123", "Test User")
        }

        binding.btnNavigateToAllStory.setOnClickListener{
            val intent = Intent(this, TestStoryActivity::class.java)
            startActivity(intent)
        }
    }
}
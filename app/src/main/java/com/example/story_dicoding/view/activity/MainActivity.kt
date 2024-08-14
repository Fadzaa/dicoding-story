package com.example.story_dicoding.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.story_dicoding.databinding.ActivityMainBinding
import com.example.story_dicoding.helper.setLoading
import com.example.story_dicoding.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        authViewModel.checkUserTokenPreferences(this)

        authViewModel.isLoading.observe(this) {
            binding.progressBar.setLoading(it)
        }

        binding.btnLogin.setOnClickListener {

            authViewModel.loginUser("fattah77@gmail.com", "fattah123").observe(this) { response ->
                if (response != null) {

                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ListStoryActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}
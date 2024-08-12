package com.example.story_dicoding.helper

import android.view.View

fun View.setLoading(isLoading: Boolean) {
    this.visibility = if (isLoading) View.VISIBLE else View.GONE
}
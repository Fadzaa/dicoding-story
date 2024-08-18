package com.example.story_dicoding

import com.example.story_dicoding.model.remote.response.Story

object DataDummy {

    fun generateDummyStoryResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                i.toString(),
                System.currentTimeMillis().toString(),
                "Description $i",
                i.toDouble(),
                i.toDouble(),
                "Name $i",
                "PhotoUrl $i",
            )
            items.add(story)
        }
        return items
    }
}
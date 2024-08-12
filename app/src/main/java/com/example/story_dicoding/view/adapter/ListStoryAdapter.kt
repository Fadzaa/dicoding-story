package com.example.story_dicoding.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.story_dicoding.R
import com.example.story_dicoding.databinding.ItemStoryVerticalBinding
import com.example.story_dicoding.model.remote.response.Story

class ListStoryAdapter(private val listStory: List<Story>) : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemStoryVerticalBinding = ItemStoryVerticalBinding.bind(itemView)

        fun bind(story: Story) {
            with(binding) {

                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivStory)

                tvStoryName.text = story.name
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListStoryAdapter.ListViewHolder {
        val view = View.inflate(parent.context, R.layout.item_story_vertical, null)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListStoryAdapter.ListViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

}
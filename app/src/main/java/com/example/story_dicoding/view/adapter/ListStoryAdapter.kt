package com.example.story_dicoding.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.story_dicoding.view.activity.DetailStoryActivity
import com.example.story_dicoding.R
import com.example.story_dicoding.databinding.ItemStoryVerticalBinding
import com.example.story_dicoding.model.remote.response.Story

class ListStoryAdapter :  PagingDataAdapter<Story, ListStoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemStoryVerticalBinding = ItemStoryVerticalBinding.bind(itemView)

        fun bind(story: Story) {
            with(binding) {

                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .into(ivStory)

                tvName.text = story.name
                tvDescription.text = story.description
            }



        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story_vertical, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)

        data?.let {
            holder.bind(it)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_STORY, data)
                holder.itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}
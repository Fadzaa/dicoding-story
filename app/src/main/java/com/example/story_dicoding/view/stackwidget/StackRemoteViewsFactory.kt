package com.example.story_dicoding.view.stackwidget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.story_dicoding.R
import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.preferences.dataStore
import com.example.story_dicoding.model.remote.ApiConfig
import com.example.story_dicoding.model.remote.response.AllStoryResponse
import com.example.story_dicoding.model.remote.response.Story
import com.example.story_dicoding.model.repository.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class StackRemoteViewsFactory(private val mContext: Context): RemoteViewsService.RemoteViewsFactory  {
    private val mWidgetItems = ArrayList<Story>()
    private val apiService = ApiConfig.getApiService(SettingPreferences.getInstance(mContext.dataStore))

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        apiService.getAllStory(1, 1, 0).enqueue(
            object : Callback<AllStoryResponse> {
                override fun onResponse(
                    call: Call<AllStoryResponse>,
                    response: Response<AllStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            for (story in it.listStory) {
                                mWidgetItems.add(story)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                    Log.e("StackRemoteViewsFactory", "onFailure: ${t.message.toString()}")
                }
            }
        )
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)
        val extras = bundleOf(
            StoryBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.image_view, fillInIntent)
        return rv

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

//    fun getAllStory(): AllStoryResponse {
//        var allStoryResponse: AllStoryResponse? = null
//
//        apiService.getAllStory(1, 1, 0).enqueue(
//            object : Callback<AllStoryResponse> {
//                override fun onResponse(
//                    call: Call<AllStoryResponse>,
//                    response: Response<AllStoryResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let {
//                            allStoryResponse = it
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
////                    Log.e(, "onFailure: ${t.message.toString()}")
//                }
//            }
//        )
//
//        return allStoryResponse!!
//    }
}
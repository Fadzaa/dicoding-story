package com.example.story_dicoding.view.stackwidget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.story_dicoding.R
import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.remote.ApiConfig
import com.example.story_dicoding.model.remote.response.Story
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

internal class StackRemoteViewsFactory(private val mContext: Context, settingPreferences: SettingPreferences): RemoteViewsService.RemoteViewsFactory  {
    private val mWidgetItems = ArrayList<Story>()
    private val apiService = ApiConfig.getApiService(settingPreferences)

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val latch = CountDownLatch(1)

        GlobalScope.launch {
            val response = apiService.getAllStory(1, 10, 0)

            if (response.isSuccessful) {
                response.body()?.let {
                    for (story in it.listStory) {
                        mWidgetItems.add(story)
                    }
                }

            }
        }

        try {
            latch.await(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

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
        val imageBitmap = Glide.with(mContext)
            .asBitmap()
            .load(mWidgetItems[position].photoUrl)
            .submit()
            .get()

        rv.setImageViewBitmap(R.id.image_view, imageBitmap)

        return rv
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(mContext.packageName, R.layout.loading_view)
    }

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}
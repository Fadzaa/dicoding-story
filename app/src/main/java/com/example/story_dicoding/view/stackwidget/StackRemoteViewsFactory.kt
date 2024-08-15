package com.example.story_dicoding.view.stackwidget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.example.story_dicoding.R

internal class StackRemoteViewsFactory(private val mContext: Context): RemoteViewsService.RemoteViewsFactory  {
    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.logo))
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.logo))
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.item_widget)
        rv.setImageViewBitmap(R.id.image_view, mWidgetItems[position])
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
}
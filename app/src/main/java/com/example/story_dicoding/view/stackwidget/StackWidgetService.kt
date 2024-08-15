package com.example.story_dicoding.view.stackwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.widget.RemoteViewsService
import com.example.story_dicoding.model.preferences.SettingPreferences
import com.example.story_dicoding.model.preferences.dataStore

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext, SettingPreferences.getInstance(this.applicationContext.dataStore))
    }
}
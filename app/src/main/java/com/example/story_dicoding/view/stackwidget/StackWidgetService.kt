package com.example.story_dicoding.view.stackwidget

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}
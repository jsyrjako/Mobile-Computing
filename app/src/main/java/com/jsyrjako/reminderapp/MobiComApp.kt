package com.jsyrjako.reminderapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobiComApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
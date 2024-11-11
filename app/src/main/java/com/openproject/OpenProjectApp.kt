package com.openproject

import android.app.Application
import com.openproject.util.LogginTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class OpenProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LogginTree())
    }
}
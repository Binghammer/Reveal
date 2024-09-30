package com.openproject

import android.app.Application
import com.openproject.log.LogginTree
import timber.log.Timber

class OpenProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LogginTree())
        Timber.d("OpenProjectApp.onCreate:")
    }
}
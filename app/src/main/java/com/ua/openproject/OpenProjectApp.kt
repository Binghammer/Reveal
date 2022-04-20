package com.ua.openproject

import android.app.Application
import com.ua.openproject.log.LogginTree
import timber.log.Timber

class OpenProjectApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LogginTree())
        Timber.d("OpenProjectApp.onCreate:")
    }
}
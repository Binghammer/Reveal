package com.openproject.util

import android.util.Log
import timber.log.Timber

class LogginTree : Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, filterableTag(tag, priority), message, t)
    }
    private fun filterableTag(tag: String?, priority: Int): String {
        return tag?.takeIf { it.startsWith("@") } ?: when (priority) {
            Log.ASSERT -> "@@@@@"
            Log.ERROR -> "@@@@"
            Log.WARN -> "@@@"
            Log.VERBOSE -> "@"
            else -> "@@" //Log.DEBUG, Log.INFO
        }
    }
}
package com.openproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ua.openproject.R
import com.openproject.service.ServiceProvider
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    lateinit var provider: ServiceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("MainActivity.onCreate:")
        provider = ServiceProvider(this)
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity.onResume:")
    }
}
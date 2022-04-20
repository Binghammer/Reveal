package com.ua.openproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ua.openproject.service.ServiceProvider
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var provider :ServiceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("MainActivity.onCreate:")
        provider = ServiceProvider(this)
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity.onResume:")
        provider.okHttp.character(listOf("1", "2", "3", Int.MAX_VALUE.toString()))
            .map { it.sortedByDescending { character ->  character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
            }, {
                Timber.e(it,"MainActivity.onResume.okHttp.character:")
            })
    }
}
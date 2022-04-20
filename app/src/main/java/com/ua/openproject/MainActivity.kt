package com.ua.openproject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ua.openproject.character.RickAdapter
import com.ua.openproject.service.ServiceProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
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
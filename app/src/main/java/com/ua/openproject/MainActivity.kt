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

    private lateinit var provider: ServiceProvider
    private lateinit var recyclerview: RecyclerView
    private lateinit var rickAdapter : RickAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("MainActivity.onCreate:")
        provider = ServiceProvider(this)

        recyclerview = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        rickAdapter = RickAdapter(this)
        recyclerview.adapter = rickAdapter
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity.onResume:")
        provider.okHttp.character((1..101).toList().map { it.toString() })
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sortedByDescending { character -> character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
                rickAdapter.list  = it
            }, {
                Timber.e(it, "MainActivity.onResume.okHttp.character:")
            })
    }
}
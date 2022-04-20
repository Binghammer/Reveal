package com.ua.openproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.ua.openproject.service.RickService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val rickService = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    val okHttp = Retrofit.Builder().client(rickService).baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RickService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("MainActivity.onCreate:")
    }

    override fun onResume() {
        super.onResume()
        okHttp.character(listOf("1", "2", "3", Int.MAX_VALUE.toString()))
            .map { it.sortedByDescending { character ->  character.name } }
            .subscribe({
                Timber.d("MainActivity.onResume.okHttp.character:$it")
            }, {
                Timber.e(it,"MainActivity.onResume.okHttp.character:")
            })
    }
}
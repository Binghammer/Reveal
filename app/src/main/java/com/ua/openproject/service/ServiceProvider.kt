package com.ua.openproject.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.ua.openproject.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider(private val context : Context) {

    val rickService = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    val okHttp: RickService = Retrofit.Builder().client(rickService).baseUrl(context.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RickService::class.java)
}
package com.openproject.data.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.openproject.data.api.RickService
import com.openproject.data.repository.RickRepository
import com.ua.openproject.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MainModule {
    private const val BASE_URL = "base_url"

    @Provides
    @Singleton
    @Named(BASE_URL)
    fun provideBaseUrl(@ApplicationContext appContext: Context): String {
        return appContext.getString(R.string.base_url)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named(BASE_URL) url: String,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRickService(
        retrofit: Retrofit,
    ): RickService {
        return retrofit.create(RickService::class.java)
    }

    @Provides
    @Singleton
    fun provideRickRepsistory(
        service: RickService,
    ): RickRepository {
        return RickRepository(service)
    }
}
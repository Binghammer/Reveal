package com.openproject.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.openproject.data.api.RickService
import com.openproject.data.repository.AppDatabase
import com.openproject.data.repository.EpisodeDao
import com.openproject.data.repository.EpisodeRepository
import com.openproject.data.repository.FigureDao
import com.openproject.data.repository.FigureRepository
import com.squareup.picasso.Picasso
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
    fun providePicasso(
        @ApplicationContext context: Context,
    ): Picasso {
        return Picasso.Builder(context).build()
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
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, "rick")
            .build()
    }

    @Provides
    @Singleton
    fun provideFigureDao(
        appDatabase: AppDatabase,
    ): FigureDao {
        return appDatabase.figureDao()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(
        appDatabase: AppDatabase,
    ): EpisodeDao {
        return appDatabase.episodeDao()
    }

    @Provides
    @Singleton
    fun provideFigureRepository(
        service: RickService,
        figureDao: FigureDao,
        episodeRepository: EpisodeRepository,
    ): FigureRepository {
        return FigureRepository(service, figureDao, episodeRepository)
    }

    @Provides
    @Singleton
    fun provideEpisodeRepository(
        service: RickService,
        episodeDao: EpisodeDao,
    ): EpisodeRepository {
        return EpisodeRepository(service, episodeDao)
    }
}
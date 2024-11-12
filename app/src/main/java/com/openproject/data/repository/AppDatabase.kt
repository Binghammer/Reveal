package com.openproject.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openproject.data.model.Figure

@Database(
    entities = [
        Figure::class
    ],
    version = 1
)
@TypeConverters(
    LocationConverter::class,
    OriginConverter::class,
    ListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun figureDao(): FigureDao

}
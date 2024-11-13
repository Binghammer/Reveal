package com.openproject.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openproject.data.model.Location

class LocationConverter {

    private val gson: Gson by lazy { GsonBuilder().create() }

    @TypeConverter
    fun convertLocationFromString(string: String): Location {
        return gson.fromJson(string, Location::class.java)
    }

    @TypeConverter
    fun convertLocationToString(location: Location): String {
        return gson.toJson(location)
    }

}
package com.openproject.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openproject.data.model.Origin

class OriginConverter {

    private val gson: Gson by lazy { GsonBuilder().create() }

    @TypeConverter
    fun convertOriginFromString(string: String): Origin {
        return gson.fromJson(string, Origin::class.java)
    }

    @TypeConverter
    fun convertOriginToString(origin: Origin): String {
        return gson.toJson(origin)
    }
}
package com.openproject.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.openproject.util.TypedUtils

class ListConverter {

    private val type = TypedUtils.stringListType

    private val gson: Gson by lazy { GsonBuilder().create() }

    @TypeConverter
    fun convertListFromString(string: String): List<String> {
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        return gson.toJson(list, type)
    }
}
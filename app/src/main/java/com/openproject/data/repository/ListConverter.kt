package com.openproject.data.repository

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ListConverter {

    private val type = object : ParameterizedType {
        override fun getActualTypeArguments(): Array<Type> = arrayOf(String::class.java)

        override fun getRawType(): Type = String::class.java

        override fun getOwnerType(): Type? = null
    }

    private val gson: Gson by lazy { GsonBuilder().create() }

    @TypeConverter
    fun convertListFromString(string: String): List<String> {
        return gson.fromJson(string, type)
    }

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        return gson.toJson(list)
    }
}
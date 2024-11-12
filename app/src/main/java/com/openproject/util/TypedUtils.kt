package com.openproject.util

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object TypedUtils {
    val stringListType: Type = listType<String>()

    inline fun <reified T : Any> listType(): Type {
        return wrappedType<List<*>, T>()
    }

    inline fun <reified T : Any, reified V : Any> wrappedType(): Type {
        return object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(V::class.java)

            override fun getRawType(): Type = T::class.java

            override fun getOwnerType(): Type? = null
        }
    }
}
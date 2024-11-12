package com.openproject.data.api

import com.openproject.data.model.FiguresResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickService {
    @GET("character")
    fun figures(@Query("page") page: Int?): Call<FiguresResponse>
}

package com.openproject.data.api

import com.openproject.data.model.EpisodesResponse
import com.openproject.data.model.FiguresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickService {
    @GET("character")
    suspend fun figures(@Query("page") page: Int?): Response<FiguresResponse>

    @GET("episode")
    suspend fun episodes(@Query("page") page: Int?): Response<EpisodesResponse>
}

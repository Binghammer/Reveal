package com.openproject.data.api

import com.openproject.data.model.Figure
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RickService {
    @GET("/api/character/{character_numbers}")
    fun figures(
        @Path("character_numbers", encoded = true) numbers: List<String>,
    ): Single<List<Figure>>
}

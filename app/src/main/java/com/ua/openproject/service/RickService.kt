package com.ua.openproject.service

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RickService {
    @GET("/api/character/{character_numbers}")
    fun character(@Path("character_numbers", encoded = true) numbers: List<String>): Observable<List<Character>>
}

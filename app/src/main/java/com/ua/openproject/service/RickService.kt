package com.ua.openproject.service

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface RickService {
    @GET("/character/{numbers}")
    fun character(numbers: String): Observable<List<Character>>
}

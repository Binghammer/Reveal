package com.openproject.data.repository

import com.openproject.data.model.Character
import com.openproject.data.api.RickService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RickRepository @Inject constructor(private val rickService: RickService) {

    fun character(numbers: List<String>): Observable<List<Character>> {
        //TODO Cache
        //TODO Switch to coroutines
        return rickService.character(numbers)
    }

}
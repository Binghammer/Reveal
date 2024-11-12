package com.openproject.data.repository

import com.openproject.data.api.RickService
import com.openproject.data.model.Character
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class RickRepository @Inject constructor(private val rickService: RickService) {

    fun getCharacters(numbers: List<String>): Observable<List<Character>> {
        //TODO Cache
        //TODO Switch to coroutines
        return rickService.character(
            numbers)
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.sortedByDescending { character -> character.name } }
    }

    fun getCharacter(id: String) : Observable<Character> {
        return getCharacters(listOf("" + id))
            .map { it.first() }
    }

}
package com.openproject.data.repository

import android.annotation.SuppressLint
import com.openproject.data.api.RickService
import com.openproject.data.model.Character
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RickRepository @Inject constructor(
    private val rickService: RickService,
    private val characterDao: CharacterDao,
) {

    fun getCharacters(numbers: List<String>): Observable<List<Character>> {
        fetchCharacters(numbers)
        return characterDao.getCharacters(100)
    }

    fun getCharacter(id: Int): Single<Character> {
        return characterDao.getCharacter(id)
    }

    @SuppressLint("CheckResult")
    private fun fetchCharacters(numbers: List<String>) {
        rickService
            .character(numbers)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(characterDao::insertCharacters, Timber::e)
    }

}
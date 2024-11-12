package com.openproject.data.repository

import android.annotation.SuppressLint
import com.openproject.data.api.RickService
import com.openproject.data.model.Figure
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RickRepository @Inject constructor(
    private val rickService: RickService,
    private val figureDao: FigureDao,
) {

    fun getFigures(numbers: List<String>): Observable<List<Figure>> {
        fetchFigures(numbers)
        return figureDao.getFigures(100)
            .subscribeOn(Schedulers.io())
    }

    fun getFigure(id: Int): Single<Figure> {
        return figureDao.getFigure(id)
            .subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    private fun fetchFigures(numbers: List<String>) {
        rickService
            .figures(numbers)
            .subscribeOn(Schedulers.io())
            .subscribe(figureDao::insertFigures, Timber::e)
    }

}
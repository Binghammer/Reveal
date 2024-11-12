package com.openproject.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.openproject.data.api.RickService
import com.openproject.data.model.Figure
import com.ua.openproject.R
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class RickRepository @Inject constructor(
    @ApplicationContext private val context: Context,
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
            .map {
                it.apply {
                    map { figure ->
                        if (figure.species.isBlank()) {
                            figure.species = context.getString(R.string.subspecies)
                        }
                    }
                }
            }
            .subscribe(figureDao::insertFigures, Timber::e)
    }

}
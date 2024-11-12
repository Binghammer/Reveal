package com.openproject.data.repository

import android.content.Context
import com.openproject.data.api.RickService
import com.openproject.data.model.Figure
import com.openproject.data.model.FiguresResponse
import com.ua.openproject.R
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RickRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val rickService: RickService,
    private val figureDao: FigureDao,
) {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        //only get data once per session
        scope.launch {
            Paginate().fetch()
        }
    }

    fun getFigures(): Observable<List<Figure>> {
        return figureDao.getFigures()
            .map { it.sortedBy(Figure::name) }
            .subscribeOn(Schedulers.io())
    }

    fun getFigure(id: Int): Single<Figure> {
        return figureDao.getFigure(id)
            .subscribeOn(Schedulers.io())
    }

    private fun fetchFigures(page: Int?): FiguresResponse? {
        val response = rickService.figures(page).execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to get page: ${page?.let { 1 }} response.errorBody()")
            null
        }
    }

    inner class Paginate {
        private var page: Int? = null
        private var totalPages: Int = 0

        fun fetch() {
            Timber.d("Fetching page $page/$totalPages")
            fetchFigures(page)?.let { response ->
                page = response.info.next?.split("?page=")?.lastOrNull()?.toIntOrNull()
                totalPages = response.info.pages

                // We could prevent unnecessary API calls  by comparing
                // the "info.count" to our figure.count. But they can be
                // updated often.

                //Could either save as we go, or return when done. Depends on specs
                figureDao.insertFigures(normalize(response.results))
            }

            if (page != null) {
                fetch()
            }
        }

        private fun normalize(figures: List<Figure>): List<Figure> {
            return figures.onEach { figure ->
                if (figure.species.isBlank()) {
                    figure.species = context.getString(R.string.subspecies)
                }
            }
        }
    }
}
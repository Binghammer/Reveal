package com.openproject.data.repository

import com.openproject.data.api.RickService
import com.openproject.data.model.Figure
import com.openproject.data.model.FigureEpisodeCrossReference
import com.openproject.data.model.FigureWithEpisodes
import com.openproject.data.model.FiguresResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FigureRepository @Inject constructor(
    private val rickService: RickService,
    private val figureDao: FigureDao,
    private val episodeRepository: EpisodeRepository,
) {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        //only get data once per session
        scope.launch {
            val figures = Paginate().fetch()
            episodeRepository.run()
            defineEpisodes(figures)
        }
    }

    private fun defineEpisodes(figures: List<Figure>) {
        val crossRefs = mutableListOf<FigureEpisodeCrossReference>()
        figures.forEach { figure ->
            crossRefs.addAll(figure.episodes
                .map { episode ->
                    val episodeId = episode.split("/").last().toInt()
                    FigureEpisodeCrossReference(figure.id, episodeId)
                })
        }

        figureDao.insertFigureEpisodeCrossReferences(crossRefs)
    }

    fun getFigures(): Observable<List<Figure>> {
        return figureDao.getFigures()
            .map { it.sortedBy(Figure::name) }
            .subscribeOn(Schedulers.io())
    }

    fun getFigure(id: Int): Single<FigureWithEpisodes> {
        return figureDao.getFigureWithEpisodes(id)
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

        fun fetch(): List<Figure> {
            Timber.d("Fetching page $page/$totalPages")
            fetchFigures(page)?.let { response ->
                page = response.info.next?.split("?page=")?.lastOrNull()?.toIntOrNull()
                totalPages = response.info.pages

                // We could prevent unnecessary API calls  by comparing
                // the "info.count" to our figure.count. But they can be
                // updated often.

                //Could either save as we go, or return when done. Depends on specs
                figureDao.insertFigures(response.results)

                if (page != null) {
                    return response.results + fetch()
                }
            }

            return emptyList()
        }
    }
}
package com.openproject.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.openproject.data.api.RickService
import com.openproject.data.model.Figure
import com.openproject.data.model.FigureEpisodeCrossReference
import com.openproject.data.model.FigureWithEpisodes
import com.openproject.data.model.FiguresResponse
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

    private suspend fun defineEpisodes(figures: List<Figure>) {
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

    fun getFigures(): LiveData<List<Figure>> {
        return figureDao.getFigures().map { it.sortedBy(Figure::name) }
    }

    suspend fun getFigure(id: Int): FigureWithEpisodes {
        return figureDao.getFigureWithEpisodes(id)
    }

    private suspend fun fetchFigures(page: Int?): FiguresResponse? {
        val response = rickService.figures(page)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to get page: ${page?.let { 1 }} response.errorBody()")
            null
        }
    }

    inner class Paginate {
        private var page: Int? = null
        private var totalPages: String = "?"

        suspend fun fetch(): List<Figure> {
            Timber.d("Fetching page $page/$totalPages")
            fetchFigures(page)?.let { response ->
                page = response.info.next?.split("?page=")?.lastOrNull()?.toIntOrNull()
                totalPages = response.info.pages.toString()

                // We could prevent unnecessary API calls by comparing
                // the "info.count" to our figure.count, but they can be
                // updated often.

                //Could either save as we go, or return when done. Depends on requirements
                figureDao.insertFigures(response.results)

                if (page != null) {
                    return response.results + fetch()
                }
            }

            return emptyList()
        }
    }
}
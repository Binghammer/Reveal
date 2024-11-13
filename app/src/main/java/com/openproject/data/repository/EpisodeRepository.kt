package com.openproject.data.repository

import com.openproject.data.api.RickService
import com.openproject.data.model.EpisodesResponse
import timber.log.Timber
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val rickService: RickService,
    private val episodeDao: EpisodeDao,
) {

    private var page: Int? = null

    suspend fun run() {
        fetchEpisodes(page)?.let { response ->
            page = response.info.next?.split("?page=")?.lastOrNull()?.toIntOrNull()
            episodeDao.insertEpisodes(response.results)
        }

        if (page != null) {
            run()
        }
    }

    private suspend fun fetchEpisodes(page: Int?): EpisodesResponse? {
        val response = rickService.episodes(page).execute()
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.e("Failed to get page: ${page?.let { 1 }} response.errorBody()")
            null
        }
    }
}
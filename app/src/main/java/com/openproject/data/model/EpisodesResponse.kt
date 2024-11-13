package com.openproject.data.model

data class EpisodesResponse(
    val info: PageInfo,
    val results: List<Episode>,
)
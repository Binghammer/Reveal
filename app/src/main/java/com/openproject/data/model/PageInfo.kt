package com.openproject.data.model

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val previous: String?,
)

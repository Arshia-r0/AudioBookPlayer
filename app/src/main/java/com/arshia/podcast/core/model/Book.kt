package com.arshia.podcast.core.model

data class Book(
    val name: String,
    val episodeCount: Int,
)

data class Episode(
    val number: Int,
    val name: String,
)

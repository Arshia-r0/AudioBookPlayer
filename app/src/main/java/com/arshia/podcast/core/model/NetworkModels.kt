package com.arshia.podcast.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Book(
    val name: String,
    val episodeCount: Int,
)

@Serializable
data class Episode(
    @Serializable
    val number: Int,
    val name: String,
)

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: String,
    val message: String
)

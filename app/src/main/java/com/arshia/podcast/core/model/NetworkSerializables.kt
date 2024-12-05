package com.arshia.podcast.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias AuthToken = String
typealias BookId = Int
typealias EpisodeId = Int

@Serializable
data class AuthResponse(
    @SerialName("access_token")
    val accessToken: AuthToken,
    val message: String
)

@Serializable
data class AuthError(
    val message: String
)

@Serializable
data class BookResponse(
    val books: List<Book>,
)

@Serializable
data class Book(
    @SerialName("book_id")
    val bookId: BookId,
    val name: String,
    @SerialName("episode_count")
    val episodeCount: Int,
)

@Serializable
data class BookDetailsResponse(
    val episodes: Map<EpisodeId, String>
)

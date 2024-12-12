package com.arshia.podcast.core.datastore

import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import kotlinx.serialization.Serializable

@Serializable
data class PodcastPreferences(
    val theme: AppTheme = AppTheme.System,
    val authToken: AuthToken = null,
    val username: String? = null,
    val currentEpisode: Episode? = null,
    val currentBook: Book? = null,
)

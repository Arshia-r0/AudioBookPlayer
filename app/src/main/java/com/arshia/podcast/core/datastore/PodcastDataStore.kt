package com.arshia.podcast.core.datastore

import androidx.datastore.core.DataStore
import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import kotlinx.coroutines.flow.map

class PodcastDataStore(
    private val dataStore: DataStore<PodcastPreferences>
) {

    val preferences = dataStore.data.map {
        PodcastPreferences(
            theme = it.theme,
            authToken = it.authToken,
            username = it.username,
            currentEpisode = it.currentEpisode,
            currentBook = it.currentBook,
            position = it.position,
        )
    }

    suspend fun setAppTheme(theme: AppTheme) {
        dataStore.updateData {
            it.copy(theme = theme)
        }
    }

    suspend fun setAuthToken(authToken: AuthToken) {
        dataStore.updateData {
            it.copy(authToken = authToken)
        }
    }

    suspend fun setUsername(username: String?) {
        dataStore.updateData {
            it.copy(username = username)
        }
    }

    suspend fun setCurrentPlayerState(
        currentEpisode: Episode?,
        currentBook: Book?,
        position: Long?
    ) {
        dataStore.updateData {
            it.copy(
                currentEpisode = currentEpisode,
                currentBook = currentBook,
                position = position ?: 0,
            )
        }
    }

}

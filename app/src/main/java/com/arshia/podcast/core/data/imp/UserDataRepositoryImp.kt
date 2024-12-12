package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataRepositoryImp(
    private val podcastDataStore: PodcastDataStore
): UserDataRepository {

    override val userData: Flow<UserData> = podcastDataStore.preferences
        .map {
            UserData(
                theme = it.theme,
                authToken = it.authToken,
                username = it.username,
            )
        }

    override suspend fun setAppTheme(theme: AppTheme) =
        podcastDataStore.setAppTheme(theme)

    override suspend fun setAuthToken(authToken: AuthToken) =
        podcastDataStore.setAuthToken(authToken)

    override suspend fun setUsername(username: String?) =
        podcastDataStore.setUsername(username)

}

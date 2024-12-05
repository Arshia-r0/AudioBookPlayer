package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.Flow

class UserDataRepositoryImp(
    private val podcastDataStore: PodcastDataStore
): UserDataRepository {

    override val userData: Flow<UserData> = podcastDataStore.userData

    override suspend fun setAppTheme(theme: AppTheme) =
        podcastDataStore.setAppTheme(theme)

    override suspend fun setAuthToken(authToken: AuthToken) =
        podcastDataStore.setAuthToken(authToken)

    override suspend fun setUsername(username: String?) =
        podcastDataStore.setUsername(username)

}

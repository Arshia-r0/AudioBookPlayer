package com.arshia.podcast.core.data

import com.arshia.podcast.core.datastore.PodcastDataStore
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.Flow

class UserDataRepositoryImp(
    private val podcastDataStore: PodcastDataStore
): UserDataRepository {

    override val userData: Flow<UserData> = podcastDataStore.userData

    override suspend fun setAppTheme(theme: com.arshia.podcast.core.model.AppTheme) =
        podcastDataStore.setAppTheme(theme)

    override suspend fun setAuthToken(authToken: String?) =
        podcastDataStore.setAuthToken(authToken)

}
package com.arshia.podcast.core.datastore

import androidx.datastore.core.DataStore
import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.map

class PodcastDataStore(
    private val dataStore: DataStore<UserData>
) {

    val userData = dataStore.data.map {
        UserData(
            theme = it.theme,
            authToken = it.authToken
        )
    }

    suspend fun setAppTheme(theme: AppTheme) {
        dataStore.updateData {
            it.copy(theme = theme)
        }
    }

    suspend fun setAuthToken(authToken: AuthToken?) {
        dataStore.updateData {
            it.copy(authToken = authToken)
        }
    }

}

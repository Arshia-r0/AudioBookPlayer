package com.arshia.podcast.core.data.userdata

import com.arshia.podcast.core.model.AppTheme
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setAppTheme(theme: AppTheme)

    suspend fun setAuthToken(authToken: String?)

}

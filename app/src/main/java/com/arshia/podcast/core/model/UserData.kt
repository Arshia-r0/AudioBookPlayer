package com.arshia.podcast.core.model

import com.arshia.podcast.core.data.auth.AuthToken
import kotlinx.serialization.Serializable


@Serializable
data class UserData(
    val theme: AppTheme = AppTheme.System,
    val authToken: AuthToken? = null
)

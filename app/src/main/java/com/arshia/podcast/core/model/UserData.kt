package com.arshia.podcast.core.model

import kotlinx.serialization.Serializable


@Serializable
data class UserData(
    val theme: AppTheme = AppTheme.System,
    val authToken: String? = null
)

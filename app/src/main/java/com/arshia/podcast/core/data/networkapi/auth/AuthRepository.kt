package com.arshia.podcast.core.data.networkapi.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.model.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

typealias AuthToken = String

interface AuthRepository {

    suspend fun login(username: String, password: String): Flow<Resource<AuthResponse>>

    suspend fun register(username: String, password: String): Flow<Resource<AuthResponse>>

    suspend fun logout(): Flow<Resource<String>>
    
}

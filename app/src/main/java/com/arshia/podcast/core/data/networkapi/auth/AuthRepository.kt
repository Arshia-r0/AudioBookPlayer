package com.arshia.podcast.core.data.networkapi.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authParameters: AuthParameters): Flow<Resource<AuthResponse>>

    suspend fun register(authParameters: AuthParameters): Flow<Resource<AuthResponse>>

    suspend fun logout(): Flow<Resource<Nothing>>
    
}

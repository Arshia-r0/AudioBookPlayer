package com.arshia.podcast.core.data.auth

import com.arshia.podcast.core.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

typealias AuthToken = String

interface AuthRepository {

    suspend fun login(username: String, password: String): Flow<Resource<Json>>

    suspend fun register(username: String, password: String): Flow<Resource<Json>>

    suspend fun logout(): Flow<Resource<String>>
   
}

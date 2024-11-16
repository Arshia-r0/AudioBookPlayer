package com.arshia.podcast.core.data.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.common.networkFlow
import com.arshia.podcast.core.data.userdata.UserDataRepositoryImp
import com.arshia.podcast.core.network.ktor.AuthParameters
import com.arshia.podcast.core.network.ktor.NetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class KtorAuthRepository(
    private val userDataRepositoryImp: UserDataRepositoryImp,
    private val networkApi: NetworkApi,
) : AuthRepository {

    override suspend fun login(username: String, password: String): Flow<Resource<Json>> =
        networkApi.register(
            AuthParameters(username = username, password = password)
        ).networkFlow()

    override suspend fun register(username: String, password: String): Flow<Resource<Json>> =
        networkApi.login(
            AuthParameters(username = username, password = password)
        ).networkFlow()

    override suspend fun logout(): Flow<Resource<String>> = flow {
        networkApi.logout()
        userDataRepositoryImp.setAuthToken(null)
    }

}

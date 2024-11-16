package com.arshia.podcast.core.data.networkapi.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.networkapi.getNetworkResponse
import com.arshia.podcast.core.data.userdata.UserDataRepositoryImp
import com.arshia.podcast.core.model.AuthResponse
import com.arshia.podcast.core.network.ktor.AuthParameters
import com.arshia.podcast.core.network.ktor.NetworkApi
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorAuthRepository(
    private val userDataRepositoryImp: UserDataRepositoryImp,
    private val networkApi: NetworkApi,
) : AuthRepository {

    override suspend fun login(username: String, password: String): Flow<Resource<AuthResponse>> = getNetworkResponse {
        networkApi.login(
            AuthParameters(username = username, password = password)
        )
    }

    override suspend fun register(username: String, password: String): Flow<Resource<AuthResponse>> = flow {
        emit(Resource.Loading())
        val response = networkApi.register(
            AuthParameters(username = username, password = password)
        )
        emit(
            if(response.status == HttpStatusCode.OK) Resource.Success<AuthResponse>(response.body())
            else Resource.Error()
        )
    }

    override suspend fun logout(): Flow<Resource<String>> = flow {
        networkApi.logout()
        userDataRepositoryImp.setAuthToken(null)
    }

}

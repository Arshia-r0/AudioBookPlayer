package com.arshia.podcast.core.data.networkapi.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.networkapi.getSerializedResource
import com.arshia.podcast.core.data.userdata.UserDataRepositoryImp
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthResponse
import com.arshia.podcast.core.network.ktor.NetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorAuthRepository(
    private val userDataRepositoryImp: UserDataRepositoryImp,
    private val networkApi: NetworkApi,
) : AuthRepository {

    override suspend fun login(authParameters: AuthParameters): Flow<Resource<AuthResponse>> =
        getSerializedResource {
            networkApi.login(authParameters)
        }

    override suspend fun register(authParameters: AuthParameters): Flow<Resource<AuthResponse>> =
        getSerializedResource {
            networkApi.register(authParameters)
        }

    override suspend fun logout(): Flow<Resource<Nothing>> = flow {
        emit(Resource.Loading())
        networkApi.logout()
        userDataRepositoryImp.setAuthToken(null)
        emit(Resource.Success(null))
    }

}

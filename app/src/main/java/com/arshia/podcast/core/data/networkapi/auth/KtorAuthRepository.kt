package com.arshia.podcast.core.data.networkapi.auth

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.userdata.UserDataRepositoryImp
import com.arshia.podcast.core.model.AuthError
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthResponse
import com.arshia.podcast.core.model.AuthToken
import com.arshia.podcast.core.network.ktor.NetworkApi
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorAuthRepository(
    private val userDataRepositoryImp: UserDataRepositoryImp,
    private val networkApi: NetworkApi,
) : AuthRepository {

    override suspend fun login(authParameters: AuthParameters): Flow<Resource<AuthResponse>> =
        flow {
            emit(Resource.Loading())
            val response = networkApi.login(authParameters)
            if (response.status != HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else emit(Resource.Success(response.body()))
        }

    override suspend fun register(authParameters: AuthParameters): Flow<Resource<AuthResponse>> =
        flow {
        emit(Resource.Loading())
            val response = networkApi.register(authParameters)
            if (response.status != HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else emit(Resource.Success(response.body()))
        }

    override suspend fun logout(token: AuthToken): Flow<Resource<Nothing>> = flow {
        emit(Resource.Loading())
        val response = networkApi.logout(token)
        if (response.status == HttpStatusCode.OK)
            emit(Resource.Error((response.body() as AuthError).message))
        else {
            userDataRepositoryImp.setAuthToken(null)
            emit(Resource.Success(null))
        }
    }

}

package com.arshia.podcast.core.data.imp

import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.model.AuthError
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthResponse
import com.arshia.podcast.core.model.ProfileResponse
import com.arshia.podcast.core.network.ktor.NetworkApi
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KtorAuthRepository(
    private val userDataRepositoryImp: UserDataRepositoryImp,
    private val networkApi: NetworkApi,
) : AuthRepository {

    override suspend fun profile(): Flow<Resource<ProfileResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = networkApi.profile()
            println(response.body() as ProfileResponse)
            if (response.status != HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else emit(Resource.Success(response.body()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        }
    }

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
            try {
                emit(Resource.Loading())
                val response = networkApi.register(authParameters)
                if (response.status != HttpStatusCode.OK)
                    emit(Resource.Error((response.body() as AuthError).message))
                else emit(Resource.Success(response.body()))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage ?: "error"))
            }
        }

    override suspend fun logout(): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            val response = networkApi.logout()
            if (response.status == HttpStatusCode.OK)
                emit(Resource.Error((response.body() as AuthError).message))
            else {
                userDataRepositoryImp.setAuthToken(null)
                emit(Resource.Success(null))
            }
        } catch (e: Exception) {
            println(e.localizedMessage ?: "")
            emit(Resource.Error(message = e.localizedMessage ?: "error"))
        }
    }

}

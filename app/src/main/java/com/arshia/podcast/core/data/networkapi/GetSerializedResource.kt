package com.arshia.podcast.core.data.networkapi

import com.arshia.podcast.core.common.Resource
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified T> getSerializedResource(
    crossinline networkCall: suspend () -> HttpResponse
): Flow<Resource<T>> = flow {
    emit((Resource.Loading()))
    val response = networkCall()
    emit(
        if(response.status == HttpStatusCode.OK) Resource.Success<T>(response.body())
        else Resource.Error()
    )
}

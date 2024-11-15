package com.arshia.podcast.core.common

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

fun HttpResponse.networkFlow(): Flow<Resource<Json>> = flow {
    emit(Resource.Loading<Json>())
    val response = this@networkFlow
    emit(
        if (response.status == HttpStatusCode.OK) Resource.Success<Json>(response.body())
        else Resource.Error<Json>()
    )
}

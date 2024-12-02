package com.arshia.podcast.core.network.ktor

import com.arshia.podcast.core.model.AuthParameters
import io.ktor.client.statement.HttpResponse

interface NetworkApi {

    suspend fun login(authParameters: AuthParameters): HttpResponse

    suspend fun register(authParameters: AuthParameters): HttpResponse

    suspend fun logout(): HttpResponse

    suspend fun getBooks(): HttpResponse

    suspend fun getBookDetails(id: Int): HttpResponse

}

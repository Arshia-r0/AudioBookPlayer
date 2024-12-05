package com.arshia.podcast.core.network.ktor

import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthToken
import io.ktor.client.statement.HttpResponse

interface NetworkApi {

    suspend fun login(authParameters: AuthParameters): HttpResponse

    suspend fun register(authParameters: AuthParameters): HttpResponse

    suspend fun logout(token: AuthToken): HttpResponse

    suspend fun getBooks(token: AuthToken): HttpResponse

    suspend fun getBookDetails(id: Int, token: AuthToken): HttpResponse

}

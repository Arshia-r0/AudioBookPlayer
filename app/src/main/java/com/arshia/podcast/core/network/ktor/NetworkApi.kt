package com.arshia.podcast.core.network.ktor

import kotlinx.serialization.json.Json

interface NetworkApi {

    suspend fun login(username: String, password: String): Json

    suspend fun register(username: String, password: String): Json

    suspend fun logout(): Json

    suspend fun getBooks(): Json

    suspend fun getBookDetails(): Json

}

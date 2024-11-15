package com.arshia.podcast.core.network.ktor

import androidx.lifecycle.asLiveData
import com.arshia.podcast.core.data.UserDataRepositoryImp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.serialization.json.Json

class KtorNetworkApi(
    private val client: HttpClient,
    userDataRepositoryImp: UserDataRepositoryImp
) : NetworkApi {

    var token = userDataRepositoryImp.userData.asLiveData()

    override suspend fun login(username: String, password: String): Json =
        client.post("login").body()

    override suspend fun register(username: String, password: String): Json =
        client.post("register").body()

    override suspend fun logout(): Json =
        client.post {
            append(url = "logout", token = token.value?.authToken)
        }.body()

    override suspend fun getBooks(): Json =
        client.get {
            append(url = "book", token = token.value?.authToken)
        }.body()

    override suspend fun getBookDetails(): Json =
        client.get {
            append(url = "book/1", token = token.value?.authToken)
        }.body()

}

fun HttpRequestBuilder.append(url: String, token: String? = null) {
    url {
        path(url)
    }
    token?.let {
        headers {
            append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}

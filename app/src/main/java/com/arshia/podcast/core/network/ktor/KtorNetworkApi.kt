package com.arshia.podcast.core.network.ktor

import androidx.lifecycle.asLiveData
import com.arshia.podcast.core.data.userdata.UserDataRepositoryImp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.path

class KtorNetworkApi(
    private val client: HttpClient,
    userDataRepositoryImp: UserDataRepositoryImp
) : NetworkApi {

    var token = userDataRepositoryImp.userData.asLiveData()

    override suspend fun login(authParameters: AuthParameters): HttpResponse =
        client.post {
            append(url = "login", authParameters = authParameters)
        }.body()

    override suspend fun register(authParameters: AuthParameters): HttpResponse =
        client.post {
            append(url = "register", authParameters = authParameters)
        }.body()

    override suspend fun logout(): HttpResponse =
        client.post {
            append(url = "logout", token = token.value?.authToken)
        }

    override suspend fun getBooks(): HttpResponse =
        client.get {
            append(url = "book", token = token.value?.authToken)
        }

    override suspend fun getBookDetails(): HttpResponse =
        client.get {
            append(url = "book/1", token = token.value?.authToken)
        }.body()

}

fun HttpRequestBuilder.append(
    url: String,
    token: String? = null,
    authParameters: AuthParameters? = null
) {
    url {
        path(url)
        authParameters?.let {
            parameters.append(name = "user_name", value = authParameters.username)
            parameters.append(name = "password", value = authParameters.password)
        }
    }
    token?.let {
        headers {
            append(HttpHeaders.Authorization, "Bearer $it")
        }
    }
}

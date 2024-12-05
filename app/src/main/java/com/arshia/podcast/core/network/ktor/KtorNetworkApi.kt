package com.arshia.podcast.core.network.ktor

import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.model.AuthToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.path

class KtorNetworkApi(
    private val client: HttpClient,
) : NetworkApi {

    override suspend fun login(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "login",
            formParameters = Parameters.build {
                append("user_name", authParameters.username)
                append("password", authParameters.password)
            }
        ).body()

    override suspend fun register(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "register",
            formParameters = Parameters.build {
                append("user_name", authParameters.username)
                append("password", authParameters.password)
            }
        ).body()

    override suspend fun logout(token: AuthToken): HttpResponse =
        client.post {
            append(url = "logout", token = token)
        }

    override suspend fun getBooks(token: AuthToken): HttpResponse =
        client.get {
            append(url = "book", token = token)
        }

    override suspend fun getBookDetails(id: Int, token: AuthToken): HttpResponse =
        client.get {
            append(url = "book/{id}", token = token)
        }.body()

}

fun HttpRequestBuilder.append(
    url: String,
    token: String? = null,
) {
    url {
        path(url)
    }
    token?.let {
        headers {
            append(HttpHeaders.Authorization, "Bearer $it")
        }
    }
}

package com.arshia.podcast.core.network.ktor

import com.arshia.podcast.core.model.AuthParameters
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.http.path

class KtorNetworkApi(
    private val client: HttpClient,
) : NetworkApi {

    override suspend fun profile(): HttpResponse =
        client.get {
            url {
                path("profile")
            }
        }

    override suspend fun login(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "login",
            formParameters = Parameters.build {
                append("user_name", authParameters.username)
                append("password", authParameters.password)
            }
        )

    override suspend fun register(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "register",
            formParameters = Parameters.build {
                append("user_name", authParameters.username)
                append("password", authParameters.password)
            }
        )

    override suspend fun logout(): HttpResponse =
        client.post {
            url {
                path("logout")
            }
        }

    override suspend fun getBooks(): HttpResponse =
        client.get {
            url {
                path("book")
            }
        }

    override suspend fun getBookDetails(bookId: Int): HttpResponse =
        client.get {
            url {
                path("book/$bookId")
            }
        }

}

package com.arshia.podcast.core.network.ktor

import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.AuthParameters
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class KtorClient(
    private val userDataRepository: UserDataRepository,
    private val scope: CoroutineScope
) : NetworkApi {

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(CIO) {
        defaultRequest {
            url("http://10.0.2.2:8000/api/")
            headers {
                headers.append(HttpHeaders.Accept, "application/json")
            }
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    allowTrailingComma = true
                    ignoreUnknownKeys = true
                }
            )
        }
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val clientException = exception as? ClientRequestException
                    ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                if (exceptionResponse.status == HttpStatusCode.Unauthorized)
                    userDataRepository.setAuthToken(null)
            }
        }
    }

    private var token: String? = null

    init {
        scope.launch {
            userDataRepository.userData.collect {
                token = it.authToken
            }
        }
    }

    override suspend fun login(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "login",
            formParameters = Parameters.build {
                append("username", authParameters.username)
                append("password", authParameters.password)
            }
        )

    override suspend fun register(authParameters: AuthParameters): HttpResponse =
        client.submitForm(
            url = "register",
            formParameters = Parameters.build {
                append("username", authParameters.username)
                append("password", authParameters.password)
            },
        )

    override suspend fun profile(): HttpResponse =
        client.get {
            url {
                path("profile")
            }
            appendToken()
        }

    override suspend fun logout(): HttpResponse =
        client.post {
            url {
                path("logout")
            }
            appendToken()
        }

    override suspend fun getBooks(): HttpResponse =
        client.get {
            url {
                path("book")
            }
            appendToken()
        }

    override suspend fun getBookDetails(bookId: Int): HttpResponse =
        client.get {
            url {
                path("book/$bookId")
            }
            appendToken()
        }

    private fun HttpRequestBuilder.appendToken() {
        token?.let {
            headers {
                append(HttpHeaders.Authorization, "Bearer $it")
            }
        }
    }

}

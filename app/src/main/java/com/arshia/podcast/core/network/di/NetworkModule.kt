package com.arshia.podcast.core.network.di

import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.network.ktor.KtorNetworkApi
import com.arshia.podcast.core.network.ktor.NetworkApi
import com.arshia.podcast.core.network.util.ConnectivityManagerNetworkMonitor
import com.arshia.podcast.core.network.util.NetworkMonitor
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    singleOf(::ConnectivityManagerNetworkMonitor) {
        bind<NetworkMonitor>()
    }

    singleOf(::KtorNetworkApi) {
        bind<NetworkApi>()
    }

    single {
        val userDataRepository by inject<UserDataRepository>()
        HttpClient(CIO) {
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
            install(Auth) {
                bearer {
                    loadTokens {
                        runBlocking {
                            BearerTokens(userDataRepository.userData.first().authToken ?: "", "")
                        }
                    }
                }
            }
            HttpResponseValidator { 
                handleResponseExceptionWithRequest { exception, request ->
                    val clientException = exception as? ClientRequestException
                        ?: return@handleResponseExceptionWithRequest
                    val exceptionResponse = clientException.response
                    if (exceptionResponse.status == HttpStatusCode.Unauthorized)
                        userDataRepository.setAuthToken(null)
                }
            }
        }
    }

}

package com.arshia.podcast.core.network.di

import com.arshia.podcast.core.network.ktor.KtorNetworkApi
import com.arshia.podcast.core.network.ktor.NetworkApi
import com.arshia.podcast.core.network.util.ConnectivityManagerNetworkMonitor
import com.arshia.podcast.core.network.util.NetworkMonitor
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
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
        HttpClient(CIO) {
            defaultRequest {
                url("http://10.0.2.2:8000/api/")
                headers {
                    headers.append(HttpHeaders.Accept, "application/json")
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    allowTrailingComma = true
                })
            }
        }
    }

}

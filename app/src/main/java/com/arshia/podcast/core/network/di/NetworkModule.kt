package com.arshia.podcast.core.network.di

import com.arshia.podcast.core.network.ktor.KtorClient
import com.arshia.podcast.core.network.ktor.NetworkApi
import com.arshia.podcast.core.network.util.ConnectivityManagerNetworkMonitor
import com.arshia.podcast.core.network.util.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {

    singleOf(::ConnectivityManagerNetworkMonitor) {
        bind<NetworkMonitor>()
    }

    singleOf(::KtorClient) {
        bind<NetworkApi>()
    }

    factory { CoroutineScope(Dispatchers.IO) }

}

package com.arshia.podcast.core.network.di

import com.arshia.podcast.core.network.ConnectivityManagerNetworkMonitor
import com.arshia.podcast.core.network.NetworkMonitor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {

    singleOf(::ConnectivityManagerNetworkMonitor) {
        bind<NetworkMonitor>()
    }

}
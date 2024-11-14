package com.arshia.podcast.core.network

import kotlinx.coroutines.flow.Flow


interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
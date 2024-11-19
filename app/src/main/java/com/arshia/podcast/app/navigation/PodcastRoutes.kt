package com.arshia.podcast.app.navigation

import kotlinx.serialization.Serializable

interface PodcastRoutes {
    @Serializable
    object AuthRoute

    @Serializable
    object MainRoute
    
    @Serializable
    object PLayerRoute
}

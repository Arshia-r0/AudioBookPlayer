package com.arshia.podcast.app.navigation

import kotlinx.serialization.Serializable

interface PodcastRoutes {

    @Serializable
    data object AuthRoute {
        @Serializable
        object RegisterRoute

        @Serializable
        object LoginRoute
    }

    @Serializable
    object MainRoute
    
    @Serializable
    object PLayerRoute
}

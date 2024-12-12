package com.arshia.podcast.app.navigation

import kotlinx.serialization.Serializable

sealed interface PodcastRoutes {

    @Serializable
    data object AuthRoute {
        @Serializable
        object RegisterRoute

        @Serializable
        object LoginRoute
    }

    @Serializable
    data object MainRoute

}

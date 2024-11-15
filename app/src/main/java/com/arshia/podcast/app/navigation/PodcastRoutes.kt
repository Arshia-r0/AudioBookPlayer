package com.arshia.podcast.app.navigation

import kotlinx.serialization.Serializable

interface PodcastRoutes {
    @Serializable
    object AuthRoute {
        @Serializable
        object LoginRoute

        @Serializable
        object SignupRoute
    }

    @Serializable
    object MainRoute
}

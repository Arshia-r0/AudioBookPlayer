package com.arshia.podcast.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.arshia.podcast.app.app.PodcastAppState

@Composable
fun PodcastNavigation(
    appState: PodcastAppState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { ip ->
        NavHost(
            navController = appState.navController,
            startDestination = PodcastRoutes.AuthRoute,
            modifier = Modifier.padding(ip)
        ) {
            navigation<PodcastRoutes.AuthRoute>(
                startDestination = PodcastRoutes.AuthRoute.LoginRoute
            ) {
                composable<PodcastRoutes.AuthRoute.LoginRoute>() {

                }
                composable<PodcastRoutes.AuthRoute.SignupRoute>() {

                }
            }
            composable<PodcastRoutes.MainRoute>() {

            }
        }
    }
}

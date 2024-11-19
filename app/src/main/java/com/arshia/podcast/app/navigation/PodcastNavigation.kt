package com.arshia.podcast.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arshia.podcast.app.app.PodcastAppState
import com.arshia.podcast.feature.auth.AuthScreen
import com.arshia.podcast.feature.main.MainScreen
import com.arshia.podcast.feature.player.PlayerScreen

@Composable
fun PodcastNavigation(
    appState: PodcastAppState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val navController = appState.navController
    val isOnline = appState.isOnline.collectAsStateWithLifecycle()
    LaunchedEffect(isOnline) {
        if (isOnline.value) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = "Internet is not available!",
            duration = SnackbarDuration.Indefinite,
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { ip ->
        NavHost(
            navController = navController,
            startDestination = PodcastRoutes.AuthRoute,
            modifier = Modifier.padding(ip)
        ) {
            composable<PodcastRoutes.AuthRoute> {
                AuthScreen(
                    toMainScreen = { navController.navigate(PodcastRoutes.MainRoute) }
                )
            }
            composable<PodcastRoutes.MainRoute> {
                MainScreen(
                    toPlayerScreen = { navController.navigate(PodcastRoutes.MainRoute) }
                )
            }
            composable<PodcastRoutes.PLayerRoute> {
                PlayerScreen(
                    toMainScreen = { navController.navigate(PodcastRoutes.MainRoute) }
                )
            }
        }
    }
}

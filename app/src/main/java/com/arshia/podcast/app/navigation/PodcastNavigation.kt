package com.arshia.podcast.app.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.arshia.podcast.app.MainActivityUiState
import com.arshia.podcast.app.app.PodcastAppState
import com.arshia.podcast.feature.login.LoginScreen
import com.arshia.podcast.feature.main.MainScreen
import com.arshia.podcast.feature.player.PlayerScreen
import com.arshia.podcast.feature.register.RegisterScreen

@Composable
fun PodcastNavigation(
    appState: PodcastAppState,
    uiState: MainActivityUiState.Success,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val navController = appState.navController
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    LaunchedEffect(isOffline) {
        if (!isOffline) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = "Internet is not available!",
            duration = SnackbarDuration.Indefinite,
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { ip ->
        NavHost(
            navController = navController,
            startDestination = if (uiState.data.authToken == null) PodcastRoutes.AuthRoute
            else PodcastRoutes.MainRoute,
            modifier = Modifier.padding(ip)
        ) {
            navigation<PodcastRoutes.AuthRoute>(
                startDestination = PodcastRoutes.AuthRoute.LoginRoute
            ) {
                composable<PodcastRoutes.AuthRoute.RegisterRoute> {
                    RegisterScreen(
                        isOffline = isOffline,
                        toLoginScreen = { navController.navigate(PodcastRoutes.AuthRoute.LoginRoute) }
                    )
                }
                composable<PodcastRoutes.AuthRoute.LoginRoute> {
                    LoginScreen(
                        isOffline = isOffline,
                        toRegisterScreen = { navController.navigate(PodcastRoutes.AuthRoute.RegisterRoute) }
                    )
                }
            }
            composable<PodcastRoutes.MainRoute> {
                MainScreen(
                    isOffline = isOffline,
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

package com.arshia.podcast.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.arshia.podcast.app.MainActivityUiState
import com.arshia.podcast.app.app.PodcastAppState
import com.arshia.podcast.feature.auth.login.LoginScreen
import com.arshia.podcast.feature.auth.register.RegisterScreen
import com.arshia.podcast.feature.main.MainScreen
import com.arshia.podcast.feature.setting.SettingScreen

@Composable
fun PodcastNavigation(
    appState: PodcastAppState,
    uiState: MainActivityUiState,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = if (uiState is MainActivityUiState.Authorized) PodcastRoutes.MainRoute
        else PodcastRoutes.AuthRoute,
    ) {
        navigation<PodcastRoutes.AuthRoute>(
            startDestination = PodcastRoutes.AuthRoute.LoginRoute
        ) {
            composable<PodcastRoutes.AuthRoute.RegisterRoute> {
                RegisterScreen(
                    appState = appState,
                    toLoginScreen = { navController.navigate(PodcastRoutes.AuthRoute.LoginRoute) }
                )
            }
            composable<PodcastRoutes.AuthRoute.LoginRoute> {
                LoginScreen(
                    appState = appState,
                    toRegisterScreen = { navController.navigate(PodcastRoutes.AuthRoute.RegisterRoute) }
                )
            }
        }
        composable<PodcastRoutes.MainRoute> {
            MainScreen(toSettingScreen = { navController.navigate(PodcastRoutes.SettingRoute) })
        }
        composable<PodcastRoutes.SettingRoute> {
            SettingScreen()
        }
    }
}

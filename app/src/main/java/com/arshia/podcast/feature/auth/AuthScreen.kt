package com.arshia.podcast.feature.auth

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    viewModel: AuthScreenViewModel = koinViewModel(),
    toMainScreen: () -> Unit = {},
) {

}

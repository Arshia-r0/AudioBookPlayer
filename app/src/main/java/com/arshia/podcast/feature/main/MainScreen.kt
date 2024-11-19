package com.arshia.podcast.feature.main

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    toPlayerScreen: () -> Unit = {},
) {

}

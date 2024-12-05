package com.arshia.podcast.feature.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    isOffline: Boolean,
    viewModel: MainScreenViewModel = koinViewModel(),
    toPlayerScreen: () -> Unit = {},
) {
    Text(text = "mainScreen")
}

package com.arshia.podcast.feature.player

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerScreenViewModel = koinViewModel(),
    toMainScreen: () -> Unit = {},
) {

}

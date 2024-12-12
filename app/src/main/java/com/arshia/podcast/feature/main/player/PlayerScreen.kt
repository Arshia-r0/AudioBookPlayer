package com.arshia.podcast.feature.main.player

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerScreenViewModel = koinViewModel(),
    toMainScreen: () -> Unit = {},
) {
    Text(text = "PlayerScreen")
}

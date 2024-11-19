package com.arshia.podcast.app.app

import androidx.compose.runtime.Composable
import com.arshia.podcast.app.MainActivityViewModel
import com.arshia.podcast.app.navigation.PodcastNavigation

@Composable
fun PodcastApp(
    appState: PodcastAppState,
    viewModel: MainActivityViewModel,
) {
    PodcastNavigation(
        appState = appState,
    )
}

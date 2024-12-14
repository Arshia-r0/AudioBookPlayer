package com.arshia.podcast.feature.main.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.podcast.core.audiobookcontroller.ControllerEvent
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import com.arshia.podcast.feature.main.EpisodeScreenUiState

@Composable
fun EpisodeScreen(
    book: Book,
    episodeState: EpisodeScreenUiState,
    episodes: List<Episode>,
    controllerEvent: (ControllerEvent) -> Unit,
    refresh: () -> Unit,
    toBookScreen: () -> Unit,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(episodeState) { isRefreshing = episodeState is EpisodeScreenUiState.Loading }
    BackHandler { toBookScreen() }
    Content(
        book = book,
        episodes = episodes,
        isRefreshing = isRefreshing,
        refresh = refresh,
        controllerEvent = controllerEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    book: Book,
    isRefreshing: Boolean,
    episodes: List<Episode>,
    controllerEvent: (ControllerEvent) -> Unit,
    refresh: () -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
        isRefreshing = isRefreshing,
        onRefresh = refresh
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(episodes) { i, episode ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            controllerEvent(
                                ControllerEvent.Start(
                                    episode,
                                    book,
                                    i + 1,
                                    episodes.size
                                )
                            )
                        }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "${i + 1}. " + episode.name,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = episode.duration,
                        fontSize = 15.sp,
                    )
                }
            }
        }
    }
}

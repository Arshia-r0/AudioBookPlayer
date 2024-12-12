package com.arshia.podcast.feature.main.episode

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.arshia.podcast.core.model.Book
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EpisodeScreen(
    book: Book,
    ip: PaddingValues,
    viewModel: EpisodeScreenViewModel = koinViewModel(parameters = { parametersOf(book.bookId) }),
    toBookScreen: () -> Unit,
) {
    val uiState by viewModel.uiState
    val episodesList = viewModel.episodesList
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) { isRefreshing = uiState is EpisodeScreenUiState.Loading }
    BackHandler { toBookScreen() }
    Content(
        ip = ip,
        isRefreshing = isRefreshing,
        episodesList = episodesList,
        refresh = { viewModel.getEpisodes() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    ip: PaddingValues,
    isRefreshing: Boolean,
    episodesList: MutableList<String>,
    refresh: () -> Unit,
) {
    PullToRefreshBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ip),
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
            items(episodesList) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}
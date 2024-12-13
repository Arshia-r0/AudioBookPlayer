package com.arshia.podcast.feature.main.components

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
import com.arshia.podcast.feature.main.BookScreenUiState

@Composable
fun BookScreen(
    bookState: BookScreenUiState,
    ip: PaddingValues,
    books: List<Book>,
    refresh: () -> Unit,
    toEpisodeScreen: (Book) -> Unit,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(bookState) { isRefreshing = bookState is BookScreenUiState.Loading }
    Content(
        ip = ip,
        isRefreshing = isRefreshing,
        books = books,
        refresh = refresh,
        toEpisodeScreen = toEpisodeScreen,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    ip: PaddingValues,
    isRefreshing: Boolean,
    books: List<Book>,
    refresh: () -> Unit,
    toEpisodeScreen: (Book) -> Unit,
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
            items(books) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { toEpisodeScreen(it) }
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = it.name,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${it.episodeCount} chapters",
                        fontSize = 15.sp,
                    )
                }
            }
        }
    }
}

package com.arshia.podcast.feature.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.podcast.core.model.Book
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    toPlayerScreen: () -> Unit = {},
) {
    val booksList = viewModel.booksList
    val uiState by viewModel.uiState
    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { ip ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (uiState) {
                is MainScreenUiState.Loading -> CircularProgressIndicator()
                is MainScreenUiState.Error -> {
                    Text(
                        text = (uiState as MainScreenUiState.Error).message ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is MainScreenUiState.Success -> MainScreenList(
                    ip = ip,
                    booksList = booksList,
                )
            }
        }
    }
}


@Composable
fun MainScreenList(
    ip: PaddingValues,
    booksList: SnapshotStateList<Book>,
) {
    LazyColumn(
        modifier = Modifier
            .padding(ip)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(booksList) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(15.dp),
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
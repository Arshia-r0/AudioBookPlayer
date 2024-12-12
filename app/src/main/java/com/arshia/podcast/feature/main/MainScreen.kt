package com.arshia.podcast.feature.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arshia.podcast.core.model.Book
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    toPlayerScreen: () -> Unit = {},
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val booksList = viewModel.booksList
    val uiState by viewModel.uiState
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) { isRefreshing = uiState is MainScreenUiState.Loading }
    Scaffold(
        topBar = {
            MainTopBar(
                username = username,
                logout = { viewModel.logout() },
            )
        },
        bottomBar = {}
    ) { ip ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.getBooks() }
        ) {
            when (uiState) {
                is MainScreenUiState.Error -> Text(
                    text = (uiState as MainScreenUiState.Error).message
                        ?: "Something went wrong!",
                    color = MaterialTheme.colorScheme.error
                )

                is MainScreenUiState.Success -> MainScreenList(
                    ip = ip,
                    booksList = booksList,
                )

                else -> {}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    username: String?,
    logout: () -> Unit,
) {
    TopAppBar(
        title = {},
        actions = {
            var isExpanded by remember { mutableStateOf(false) }
            IconButton(
                onClick = { isExpanded = true }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = username?.let { "logged in as $it" } ?: "loading..."
                        )
                    },
                    onClick = {}
                )
                DropdownMenuItem(
                    text = { Text(text = "settings") },
                    onClick = {},
                )
                DropdownMenuItem(
                    text = { Text(text = "logout") },
                    onClick = logout
                )
            }
        }
    )
}

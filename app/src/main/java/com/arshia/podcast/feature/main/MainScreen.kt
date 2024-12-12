package com.arshia.podcast.feature.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arshia.podcast.app.app.PodcastAppState
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.feature.main.book.BookScreen
import com.arshia.podcast.feature.main.episode.EpisodeScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    appState: PodcastAppState,
    viewModel: MainScreenViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(snackbarHostState = snackbarHostState)
) {
    val uiState by viewModel.uiState
    val username by viewModel.username.collectAsStateWithLifecycle()
    Content(
        appState = appState,
        uiState = uiState,
        username = username,
        snackbarHostState = snackbarHostState,
        scaffoldState = scaffoldState,
        viewModel = viewModel,
        logout = { viewModel.logout() },
        toBookScreen = { viewModel.toBookScreen() },
        toEpisodeScreen = { book -> viewModel.toEpisodeScreen(book) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    appState: PodcastAppState,
    uiState: MainScreenUiState,
    username: String?,
    snackbarHostState: SnackbarHostState,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: MainScreenViewModel,
    logout: () -> Unit,
    toEpisodeScreen: (Book) -> Unit,
    toBookScreen: () -> Unit,
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    LaunchedEffect(isOffline) {
        if (!isOffline) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = "No internet connection",
            duration = SnackbarDuration.Indefinite
        )
    }
    BottomSheetScaffold(
        topBar = {
            MainTopBar(
                username = username,
                logout = logout,
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        sheetDragHandle = {},
        sheetPeekHeight = 100.dp,
        scaffoldState = scaffoldState,
        sheetContent = {},
    ) { ip ->
        when (uiState) {
            is MainScreenUiState.Book -> BookScreen(
                ip = ip,
                toEpisodeScreen = toEpisodeScreen,
            )

            is MainScreenUiState.Episode -> EpisodeScreen(
                ip = ip,
                book = uiState.book,
                toBookScreen = toBookScreen
            )
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
                            text = username?.let { "logged in as: $it" } ?: "loading..."
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

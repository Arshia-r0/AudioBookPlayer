package com.arshia.podcast.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arshia.podcast.R
import com.arshia.podcast.core.audiobookcontroller.ControllerEvent
import com.arshia.podcast.core.common.util.convertToTime
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import com.arshia.podcast.feature.main.components.BookScreen
import com.arshia.podcast.feature.main.components.EpisodeScreen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    toSettingScreen: () -> Unit,
) {
    val uiState by viewModel.uiState
    val bookState by viewModel.bookState
    val episodeState by viewModel.episodeState
    val books by viewModel.books
    val episodes by viewModel.episodes
    val username by viewModel.username.collectAsStateWithLifecycle()
    val playerState by viewModel.playerState
    var playerTime by viewModel.playerTime
    LaunchedEffect(playerTime) {
        if (playerState.isPlaying) {
            while (true) {
                delay(1000)
                playerTime = playerTime?.plus(1)
            }
        }
    }
    Content(
        uiState = uiState,
        username = username,
        bookState = bookState,
        books = books,
        episodeState = episodeState,
        episodes = episodes,
        playerState = playerState,
        playerTime = playerTime,
        refreshBooks = { viewModel.getBooks() },
        refreshEpisodes = { viewModel.getEpisodes() },
        controllerEvent = { viewModel.controllerEvent(it) },
        logout = { viewModel.logout() },
        toBookScreen = { viewModel.toBookScreen() },
        toEpisodeScreen = { book -> viewModel.toEpisodeScreen(book) },
        toSettingScreen = toSettingScreen,
    )
}

@Composable
private fun Content(
    uiState: MainScreenUiState,
    books: List<Book>,
    episodes: List<Episode>,
    username: String?,
    playerState: PlayerState,
    playerTime: Int?,
    bookState: BookScreenUiState,
    episodeState: EpisodeScreenUiState,
    controllerEvent: (ControllerEvent) -> Unit,
    toSettingScreen: () -> Unit,
    refreshBooks: () -> Unit = {},
    refreshEpisodes: () -> Unit = {},
    logout: () -> Unit,
    toEpisodeScreen: (Book) -> Unit,
    toBookScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainTopBar(
                username = username,
                logout = logout,
                toSettingScreen = toSettingScreen,
            )
        },
    ) { ip ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                when (uiState) {
                    is MainScreenUiState.Book -> BookScreen(
                        books = books,
                        bookState = bookState,
                        refresh = refreshBooks,
                        toEpisodeScreen = toEpisodeScreen,
                    )

                    is MainScreenUiState.Episode -> EpisodeScreen(
                        book = uiState.book,
                        episodes = episodes,
                        episodeState = episodeState,
                        refresh = refreshEpisodes,
                        toBookScreen = toBookScreen,
                        controllerEvent = controllerEvent,
                    )
                }
            }
            Player(playerState, playerTime, controllerEvent)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    username: String?,
    logout: () -> Unit,
    toSettingScreen: () -> Unit,
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
                    onClick = toSettingScreen,
                )
                DropdownMenuItem(
                    text = { Text(text = "logout") },
                    onClick = logout
                )
            }
        }
    )
}

@Composable
fun Player(
    playerState: PlayerState,
    playerTime: Int?,
    controllerEvent: (ControllerEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = playerState.episode?.name ?: ""
        )
        Row(
            modifier = Modifier.fillMaxHeight(),
        ) {
            Text(
                text = playerTime?.convertToTime() ?: ""
            )
            Text(
                text = playerState.episode?.duration ?: ""
            )
        }
        Row(
            modifier = Modifier.fillMaxHeight(),
        ) {
            IconButton(
                onClick = { controllerEvent(ControllerEvent.Previous) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_previous),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { controllerEvent(if (playerState.isPlaying) ControllerEvent.Pause else ControllerEvent.Play) }
            ) {
                Icon(
                    painter = painterResource(if (playerState.isPlaying) R.drawable.pause else R.drawable.play_arrow),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = { controllerEvent(ControllerEvent.Next) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.skip_next),
                    contentDescription = null,
                )
            }
        }
    }
}

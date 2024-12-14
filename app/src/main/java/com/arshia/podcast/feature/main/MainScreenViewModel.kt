package com.arshia.podcast.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.audiobookcontroller.AudioBookController
import com.arshia.podcast.core.audiobookcontroller.ControllerEvent
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.Book
import com.arshia.podcast.core.model.Episode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    userDataRepository: UserDataRepository,
    audioBookController: AudioBookController,
    private val authRepository: AuthRepository,
    private val bookRepository: BookRepository,
) : ViewModel() {


    val uiState = mutableStateOf<MainScreenUiState>(MainScreenUiState.Book)
    val bookState = mutableStateOf<BookScreenUiState>(BookScreenUiState.Loading)
    val episodeState = mutableStateOf<EpisodeScreenUiState>(EpisodeScreenUiState.Loading)
    val books = mutableStateOf<List<Book>>(emptyList())
    val episodes = mutableStateOf<List<Episode>>(emptyList())
    val username = userDataRepository.userData
        .map { it.username }
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5000)
        )

    private val controller = audioBookController.Command()


    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            bookRepository.getBooks().collectLatest { response ->
                bookState.value = when (response) {
                    is Resource.Loading -> BookScreenUiState.Loading
                    is Resource.Error -> BookScreenUiState.Error(response.message)
                    is Resource.Success -> {
                        books.value = response.data?.books ?: emptyList()
                        BookScreenUiState.Success
                    }
                }
            }
        }
    }


    fun getEpisodes() {
        viewModelScope.launch {
            val book = (uiState.value as MainScreenUiState.Episode).book
            bookRepository.getBookDetails(book.bookId)
                .collectLatest { response ->
                    episodeState.value = when (response) {
                        is Resource.Loading -> EpisodeScreenUiState.Loading
                        is Resource.Error -> EpisodeScreenUiState.Error(response.message)
                        is Resource.Success -> {
                            episodes.value = response.data?.episodes ?: emptyList()
                            EpisodeScreenUiState.Success
                        }
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun toEpisodeScreen(book: Book) {
        uiState.value = MainScreenUiState.Episode(book)
    }

    fun toBookScreen() {
        uiState.value = MainScreenUiState.Book
    }

    fun controllerEvent(event: ControllerEvent) {
        when (event) {
            is ControllerEvent.Start -> controller.start(event.episode, event.book)
            is ControllerEvent.Play -> controller.play
            is ControllerEvent.Pause -> controller.pause
            is ControllerEvent.Next -> controller.next
            is ControllerEvent.Previous -> controller.previous
            is ControllerEvent.Seek -> controller.seek(event.position)
        }
    }

}

sealed interface MainScreenUiState {
    data object Book : MainScreenUiState
    data class Episode(val book: com.arshia.podcast.core.model.Book) : MainScreenUiState
}

sealed interface BookScreenUiState {
    data object Loading : BookScreenUiState
    data object Success : BookScreenUiState
    data class Error(val message: String? = null) : BookScreenUiState
}

sealed interface EpisodeScreenUiState {
    data object Loading : EpisodeScreenUiState
    data object Success : EpisodeScreenUiState
    data class Error(val message: String? = null) : EpisodeScreenUiState
}

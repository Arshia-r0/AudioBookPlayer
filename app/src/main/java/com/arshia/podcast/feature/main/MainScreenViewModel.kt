package com.arshia.podcast.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.audiobookcontroller.AudioBookController
import com.arshia.podcast.core.audiobookcontroller.ControllerEvent
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainScreenViewModel(
    userDataRepository: UserDataRepository,
    private val authRepository: AuthRepository,
    audioBookController: AudioBookController,
) : ViewModel() {


    val uiState = mutableStateOf<MainScreenUiState>(MainScreenUiState.Book)
    val username = userDataRepository.userData
        .map { it.username }
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5000)
        )

    private val controller = audioBookController.Command()


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

package com.arshia.podcast.feature.main.episode

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.model.Episode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeScreenViewModel(
    private val bookRepository: BookRepository,
    private val bookId: Int
) : ViewModel() {

    val uiState = mutableStateOf<EpisodeScreenUiState>(EpisodeScreenUiState.Loading)
    val episodesList = mutableStateListOf<Episode>()

    init {
        getEpisodes()
    }

    fun getEpisodes() {
        viewModelScope.launch {
            bookRepository.getBookDetails(bookId).collectLatest {
                when (it) {
                    is Resource.Loading -> uiState.value = EpisodeScreenUiState.Loading
                    is Resource.Error -> uiState.value = EpisodeScreenUiState.Error(it.message)
                    is Resource.Success -> {
                        episodesList.clear()
                        episodesList.addAll(it.data?.episodes ?: emptyList())
                        uiState.value = EpisodeScreenUiState.Success
                    }
                }
            }
        }
    }

}

sealed interface EpisodeScreenUiState {
    data object Loading : EpisodeScreenUiState
    data object Success : EpisodeScreenUiState
    data class Error(val message: String? = null) : EpisodeScreenUiState
}

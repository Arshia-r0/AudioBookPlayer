package com.arshia.podcast.feature.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.Book
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val userDataRepository: UserDataRepository,
    private val bookRepository: BookRepository,
) : ViewModel() {

    val uiState = mutableStateOf<MainScreenUiState>(MainScreenUiState.Loading)
    val booksList = mutableStateListOf<Book>()
    lateinit var username: String

    init {
        viewModelScope.launch {
            launch { getBooks() }
            launch { username = userDataRepository.userData.first().username ?: "" }
        }
    }

    fun getBooks() {
        viewModelScope.launch {
            bookRepository.getBooks().collectLatest {
                when (it) {
                    is Resource.Loading -> uiState.value = MainScreenUiState.Loading
                    is Resource.Error -> uiState.value = MainScreenUiState.Error(it.message)
                    is Resource.Success -> {
                        it.data?.books?.forEach { book ->
                            booksList += book
                        }
                        uiState.value = MainScreenUiState.Success
                    }
                }
            }
        }
    }

}

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data object Success : MainScreenUiState
    data class Error(val message: String?) : MainScreenUiState
}
package com.arshia.podcast.feature.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewModel(
    userDataRepository: UserDataRepository,
    private val bookRepository: BookRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val uiState = mutableStateOf<MainScreenUiState>(MainScreenUiState.Loading)
    val booksList = mutableStateListOf<Book>()
    var username = userDataRepository.userData
        .map { it.username }
        .stateIn(
            scope = viewModelScope,
            initialValue = null,
            started = SharingStarted.WhileSubscribed(5000)
        )

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            bookRepository.getBooks().collectLatest {
                when (it) {
                    is Resource.Loading -> uiState.value = MainScreenUiState.Loading
                    is Resource.Error -> uiState.value = MainScreenUiState.Error(it.message)
                    is Resource.Success -> {
                        booksList.removeAll { true }
                        booksList.addAll(it.data?.books ?: emptyList())
                        uiState.value = MainScreenUiState.Success
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

}

sealed interface MainScreenUiState {
    data object Loading : MainScreenUiState
    data object Success : MainScreenUiState
    data class Error(val message: String?) : MainScreenUiState
}

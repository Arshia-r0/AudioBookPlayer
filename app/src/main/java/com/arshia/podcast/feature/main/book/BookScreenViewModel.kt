package com.arshia.podcast.feature.main.book

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.BookRepository
import com.arshia.podcast.core.model.Book
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookScreenViewModel(
    private val bookRepository: BookRepository,
) : ViewModel() {

    val uiState = mutableStateOf<ListScreenUiState>(ListScreenUiState.Loading)
    val booksList = mutableStateListOf<Book>()

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            bookRepository.getBooks().collectLatest {
                when (it) {
                    is Resource.Loading -> uiState.value = ListScreenUiState.Loading
                    is Resource.Error -> uiState.value = ListScreenUiState.Error(it.message)
                    is Resource.Success -> {
                        booksList.clear()
                        booksList.addAll(it.data?.books ?: emptyList())
                        uiState.value = ListScreenUiState.Success
                    }
                }
            }
        }
    }

}

sealed interface ListScreenUiState {
    data object Loading : ListScreenUiState
    data object Success : ListScreenUiState
    data class Error(val message: String? = null) : ListScreenUiState
}

package com.arshia.podcast.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivityViewModel(
    private val userDataRepository: UserDataRepository,
    private val authRepository: AuthRepository,
): ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = userDataRepository.userData.map {
        if (it.authToken == null)
            return@map MainActivityUiState.Unauthorized(it)
        authRepository.profile()
            .collectLatest { r ->
                when (r) {
                    is Resource.Loading -> {}
                    is Resource.Error -> userDataRepository.setAuthToken(null)
                    is Resource.Success -> userDataRepository.setUsername(it.username)
                }
            }
        MainActivityUiState.Authorized(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Unauthorized(val data: UserData) : MainActivityUiState
    data class Authorized(val data: UserData) : MainActivityUiState
}

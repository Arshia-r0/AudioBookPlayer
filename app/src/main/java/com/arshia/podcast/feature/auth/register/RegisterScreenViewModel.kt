package com.arshia.podcast.feature.auth.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.data.imp.KtorAuthRepository
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.network.util.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val authRepository: KtorAuthRepository,
    private val userDataRepository: UserDataRepository,
    networkMonitor: NetworkMonitor,
) : ViewModel() {

    private val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = viewModelScope,
            initialValue = false,
            started = SharingStarted.WhileSubscribed(5000)
        )
    val uiState: MutableState<RegisterScreenUiState> = mutableStateOf(RegisterScreenUiState.Input)
    val errorMessage: MutableState<String?> = mutableStateOf(null)
    val usernameField = mutableStateOf("")
    val passwordField = mutableStateOf("")

    fun register() {
        if (isOffline.value) return
        if (usernameField.value.isEmpty() || passwordField.value.isEmpty()) {
            errorMessage.value = "Username and Password fields are required"
            return
        }
        if (passwordField.value.length < 8) {
            errorMessage.value = "Password must be at least 8 characters long."
            return
        }
        viewModelScope.launch {
            authRepository.register(
                AuthParameters(
                    username = usernameField.value,
                    password = passwordField.value
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> uiState.value = RegisterScreenUiState.Loading
                    is Resource.Success -> {
                        uiState.value = RegisterScreenUiState.Input
                        errorMessage.value = null
                        userDataRepository.setAuthToken(it.data?.accessToken)
                    }
                    else -> {
                        errorMessage.value = it.message
                        uiState.value = RegisterScreenUiState.Input
                    }
                }
            }
        }
    }

}

sealed interface RegisterScreenUiState {
    data object Input : RegisterScreenUiState
    data object Loading : RegisterScreenUiState
}

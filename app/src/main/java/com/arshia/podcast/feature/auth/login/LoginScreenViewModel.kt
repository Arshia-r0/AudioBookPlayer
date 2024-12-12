package com.arshia.podcast.feature.auth.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.AuthParameters
import com.arshia.podcast.core.network.util.NetworkMonitor
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepository: AuthRepository,
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
    val uiState: MutableState<LoginScreenUiState> = mutableStateOf(LoginScreenUiState.Input)
    val errorMessage: MutableState<String?> = mutableStateOf(null)
    val usernameField = mutableStateOf("")
    val passwordField = mutableStateOf("")

    fun login() {
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
            authRepository.login(
                AuthParameters(
                    username = usernameField.value,
                    password = passwordField.value
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> uiState.value = LoginScreenUiState.Loading
                    is Resource.Success -> {
                        uiState.value = LoginScreenUiState.Input
                        errorMessage.value = null
                        userDataRepository.setAuthToken(it.data?.accessToken)
                    }
                    else -> {
                        errorMessage.value = it.message
                        uiState.value = LoginScreenUiState.Input
                    }
                }
            }
        }
    }

}

sealed interface LoginScreenUiState {
    object Input : LoginScreenUiState
    object Loading : LoginScreenUiState
}

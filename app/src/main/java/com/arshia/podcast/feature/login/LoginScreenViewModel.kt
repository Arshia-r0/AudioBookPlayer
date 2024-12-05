package com.arshia.podcast.feature.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.AuthRepository
import com.arshia.podcast.core.data.UserDataRepository
import com.arshia.podcast.core.model.AuthParameters
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepository: AuthRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    
    val uiState: MutableState<LoginScreenUiState> = mutableStateOf(LoginScreenUiState.Input)
    val errorMessage: MutableState<String?> = mutableStateOf(null)
    val usernameField = mutableStateOf("")
    val passwordField = mutableStateOf("")

    fun login() {
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

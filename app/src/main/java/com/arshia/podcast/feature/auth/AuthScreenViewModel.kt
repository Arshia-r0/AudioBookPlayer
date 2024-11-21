package com.arshia.podcast.feature.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshia.podcast.core.common.Resource
import com.arshia.podcast.core.data.networkapi.auth.KtorAuthRepository
import com.arshia.podcast.core.data.userdata.UserDataRepository
import com.arshia.podcast.core.model.AuthParameters
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthScreenViewModel(
    private val authRepository: KtorAuthRepository,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val uiState: MutableState<AuthScreenUiState> = mutableStateOf(AuthScreenUiState.Input)
    val usernameField = mutableStateOf("")
    val passwordField = mutableStateOf("")

    fun login() {
        if (usernameField.value.isEmpty() || passwordField.value.isEmpty()) {
            uiState.value = AuthScreenUiState
                .Error(message = "username and password fields are required.")
            return
        }
        viewModelScope.launch {
            authRepository.login(
                AuthParameters(
                    username = usernameField.value,
                    password = passwordField.value
                )
            ).onEach {
                when (it) {
                    is Resource.Loading -> uiState.value = AuthScreenUiState.Loading
                    is Resource.Success -> {
                        userDataRepository.setAuthToken(it.data?.accessToken)
                        // clear backstack
                    }

                    else -> {
                        uiState.value = AuthScreenUiState.Error()
                        println("error")
                    }
                }
            }
        }
    }

}

sealed interface AuthScreenUiState {
    object Input : AuthScreenUiState
    object Loading : AuthScreenUiState
    data class Error(val message: String? = null) : AuthScreenUiState
}

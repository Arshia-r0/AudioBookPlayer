package com.arshia.podcast.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arshia.podcast.app.app.PodcastAppState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    appState: PodcastAppState,
    toRegisterScreen: () -> Unit = {},
    viewModel: LoginScreenViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val uiState by viewModel.uiState
    val errorMessage by viewModel.errorMessage
    var usernameField by viewModel.usernameField
    var passwordField by viewModel.passwordField
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    LaunchedEffect(isOffline) {
        if (!isOffline) return@LaunchedEffect
        snackbarHostState.showSnackbar(
            message = "Internet is not available!",
            duration = SnackbarDuration.Indefinite,
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { ip ->
        Box(
            modifier = Modifier.padding(ip),
            contentAlignment = Alignment.Center,
        ) {
            if (uiState is LoginScreenUiState.Loading) {
                CircularProgressIndicator()
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 5.dp,
                    alignment = Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .then(
                        if (uiState is LoginScreenUiState.Loading) Modifier.alpha(0.7f)
                        else Modifier
                    )
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                )
                OutlinedTextField(
                    value = usernameField,
                    onValueChange = { usernameField = it },
                    singleLine = true,
                    label = { Text("username") },
                )
                OutlinedTextField(
                    value = passwordField,
                    onValueChange = { passwordField = it },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("password") }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.CenterHorizontally,
                    )
                ) {
                    Button(
                        onClick = { viewModel.login() }
                    ) {
                        Text(text = "login")
                    }
                    Button(
                        onClick = toRegisterScreen,
                    ) {
                        Text(text = "register here")
                    }
                }
            }
        }
    }
}

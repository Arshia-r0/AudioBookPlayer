package com.arshia.podcast.feature.register

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    isOffline: Boolean,
    toLoginScreen: () -> Unit = {},
    viewModel: RegisterScreenViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState
    val errorMessage by viewModel.errorMessage
    var usernameField by viewModel.usernameField
    var passwordField by viewModel.passwordField
    Box(
        contentAlignment = Alignment.Center,
    ) {
        if (uiState is RegisterScreenUiState.Loading) {
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
                    if (uiState is RegisterScreenUiState.Loading) Modifier.alpha(0.7f)
                    else Modifier
                )
        ) {
            Text(
                text = "Register",
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
                    onClick = { if (!isOffline) viewModel.register() }
                ) {
                    Text(text = "register")
                }
                Button(
                    onClick = toLoginScreen,
                ) {
                    Text(text = "login here")
                }
            }
        }
    }
}

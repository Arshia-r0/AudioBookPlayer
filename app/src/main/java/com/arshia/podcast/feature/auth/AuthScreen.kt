package com.arshia.podcast.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun AuthScreen(
    viewModel: AuthScreenViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState
    var usernameField by viewModel.usernameField
    var passwordField by viewModel.passwordField
    Box(
        contentAlignment = Alignment.Center,
    ) {
        if (uiState is AuthScreenUiState.Loading) {
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
                    if (uiState is AuthScreenUiState.Loading) Modifier.alpha(0.7f)
                    else Modifier
                )
        ) {
            Text(
                text = "Log into your account",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = usernameField,
                onValueChange = { usernameField = it },
                singleLine = true,
                label = { Text("username") }
            )
            OutlinedTextField(
                value = passwordField,
                onValueChange = { passwordField = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("password") }
            )
            Button(
                onClick = { viewModel.login() }
            ) {
                Text(text = "login")
            }
        }
    }
}

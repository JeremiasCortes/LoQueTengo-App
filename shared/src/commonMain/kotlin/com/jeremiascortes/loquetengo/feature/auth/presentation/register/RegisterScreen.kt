package com.jeremiascortes.loquetengo.feature.auth.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import loquetengo.shared.generated.resources.Res
import loquetengo.shared.generated.resources.input_email
import loquetengo.shared.generated.resources.input_password
import loquetengo.shared.generated.resources.input_password_repeat
import loquetengo.shared.generated.resources.register_back_to_login
import loquetengo.shared.generated.resources.register_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RegisterScreen(
    state: RegisterUiState,
    snackbarHostState: SnackbarHostState,
    onAction: (RegisterAction) -> Unit,
    onNavigateToBack: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxSize()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.register_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = {
                        onAction(RegisterAction.EmailChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    label = {
                        Text(stringResource(Res.string.input_email))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = {
                        onAction(RegisterAction.PasswordChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(stringResource(Res.string.input_password))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = {
                        onAction(RegisterAction.ConfirmPasswordChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                    label = {
                        Text(stringResource(Res.string.input_password_repeat))
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next,
                    ),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onAction(RegisterAction.Submit)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.canSubmit,
                ) {
                    Text(text = stringResource(Res.string.register_title))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onNavigateToBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(Res.string.register_back_to_login))
                }
            }
        }

    }

}
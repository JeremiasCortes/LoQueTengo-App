package com.jeremiascortes.loquetengo.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import loquetengo.shared.generated.resources.Res
import loquetengo.shared.generated.resources.input_email
import loquetengo.shared.generated.resources.login_error_invalid_response
import loquetengo.shared.generated.resources.login_error_missing_credentials
import loquetengo.shared.generated.resources.login_error_rejected
import loquetengo.shared.generated.resources.login_error_unexpected
import loquetengo.shared.generated.resources.input_password
import loquetengo.shared.generated.resources.button_submit
import loquetengo.shared.generated.resources.login_title
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreen(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp),
        ) {
            Text(
                text = stringResource(Res.string.login_title),
                style = MaterialTheme.typography.headlineMedium,
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    onAction(LoginAction.EmailChanged(it))
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
                    onAction(LoginAction.PasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                label = {
                    Text(stringResource(Res.string.input_password))
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (state.canSubmit) {
                            onAction(LoginAction.Submit)
                        }
                    },
                ),
                singleLine = true,
            )

            state.error?.let { error ->
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = error.message(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onAction(LoginAction.Submit)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canSubmit,
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(stringResource(Res.string.button_submit))
                }
            }
        }
    }
}

@Composable
private fun LoginError.message(): String {
    return when (this) {
        LoginError.MissingCredentials ->
            stringResource(Res.string.login_error_missing_credentials)

        LoginError.Rejected ->
            stringResource(Res.string.login_error_rejected)

        LoginError.InvalidResponse ->
            stringResource(Res.string.login_error_invalid_response)

        LoginError.Unexpected ->
            stringResource(Res.string.login_error_unexpected)
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            state = LoginUiState(
                email = "usuario@example.com",
            ),
            onAction = {},
        )
    }
}
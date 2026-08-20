package com.jeremiascortes.loquetengo.feature.auth.presentation.login

internal data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: LoginError? = null,
) {
    val canSubmit: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                !isLoading
}

internal sealed class LoginError {
    data object MissingCredentials: LoginError()
    data object Rejected: LoginError()
    data object InvalidResponse: LoginError()
    data object Unexpected: LoginError()
}
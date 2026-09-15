package com.jeremiascortes.loquetengo.feature.auth.presentation.register

internal data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: RegisterError? = null
) {
    val canSubmit: Boolean
        get() = email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                password == confirmPassword &&
                !isLoading
}

internal sealed class RegisterError {
    data object InvalidResponse : RegisterError()
    data object InvalidEmail : RegisterError()
    data object InvalidPassword : RegisterError()
    data object InvalidConfirmPassword : RegisterError()
}
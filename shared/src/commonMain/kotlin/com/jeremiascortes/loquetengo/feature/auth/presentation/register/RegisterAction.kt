package com.jeremiascortes.loquetengo.feature.auth.presentation.register

internal sealed interface RegisterAction {
    data class EmailChanged(val value: String) : RegisterAction
    data class PasswordChanged(val value: String) : RegisterAction
    data class ConfirmPasswordChanged(val value: String) : RegisterAction
    data object Submit : RegisterAction
    data object ErrorDismissed : RegisterAction
}
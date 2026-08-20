package com.jeremiascortes.loquetengo.feature.auth.presentation.login

internal sealed interface LoginAction {

    data class EmailChanged(
        val value: String,
    ) : LoginAction

    data class PasswordChanged(
        val value: String,
    ) : LoginAction

    data object Submit : LoginAction

    data object ErrorDismissed : LoginAction
}
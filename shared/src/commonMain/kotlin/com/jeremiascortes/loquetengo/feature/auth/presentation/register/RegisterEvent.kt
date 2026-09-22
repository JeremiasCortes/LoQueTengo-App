package com.jeremiascortes.loquetengo.feature.auth.presentation.register

sealed interface RegisterEvent {
    data object ShowAccountCreatedMessage : RegisterEvent
    //data object NavigateToLogin : RegisterEvent
}
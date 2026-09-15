package com.jeremiascortes.loquetengo.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class RegisterViewModel(
    // TODO: Pendiente de implementar los repositorios
): ViewModel() {
    private  val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.EmailChanged -> updateEmail(action.value)
            is RegisterAction.PasswordChanged -> updatePassword(action.value)
            is RegisterAction.ConfirmPasswordChanged -> updateConfirmPassword(action.value)
            is RegisterAction.Submit -> {}
            is RegisterAction.ErrorDismissed -> {}
        }
    }

    private fun updateEmail(value: String) {
        _state.update {
            it.copy(
                email = value,
                error = null
            )
        }
    }

    private fun updatePassword(value: String) {
        _state.update {
            it.copy(
                password = value,
                error = null
            )
        }
    }

    private fun updateConfirmPassword(value: String) {
        _state.update {
            it.copy(
                confirmPassword = value,
                error = null
            )
        }
    }
}
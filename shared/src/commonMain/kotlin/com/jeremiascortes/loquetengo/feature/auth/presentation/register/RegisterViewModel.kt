package com.jeremiascortes.loquetengo.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: AuthSessionManager
): ViewModel() {
    private  val _state = MutableStateFlow(RegisterUiState())
    val state: StateFlow<RegisterUiState> = _state.asStateFlow()

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.EmailChanged -> updateEmail(action.value)
            is RegisterAction.PasswordChanged -> updatePassword(action.value)
            is RegisterAction.ConfirmPasswordChanged -> updateConfirmPassword(action.value)
            is RegisterAction.Submit -> {submit()}
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

    private fun clearErro() {
        _state.update { it.copy(error = null) }
    }

    /**
     * Limpiar todos los campos
     */
    private fun clearFields() {
        _state.update {
            it.copy(
                email = "",
                password = "",
                confirmPassword = "",
                error = null
            )
        }
    }

    private fun submit() {
        val currentState = _state.value

        if (currentState.isLoading) return

        if (currentState.email.isBlank()
            || currentState.password.isBlank()
            || currentState.confirmPassword.isBlank()) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                authRepository.register(
                    email = currentState.email.trim(),
                    password = currentState.password
                )

                _events.send(RegisterEvent.ShowAccountCreatedMessage)

                clearFields()

            } catch (_: Exception) {

            } finally {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}
package com.jeremiascortes.loquetengo.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.domain.error.AuthenticationException
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: AuthSessionManager,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> updateEmail(action.value)
            is LoginAction.PasswordChanged -> updatePassword(action.value)
            is LoginAction.Submit -> submit()
            is LoginAction.ErrorDismissed -> clearError()
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

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun submit() {
        val currentState = _state.value

        if (currentState.isLoading) {
            return
        }

        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _state.update { it.copy(error = LoginError.MissingCredentials) }
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
                val session = authRepository.login(
                    email = currentState.email.trim(),
                    password = currentState.password
                )

                sessionManager.startSession(session)

                _state.update { it.copy(password = "") }

            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: AuthenticationException) {
                handleAuthenticationError(exception)
            } catch (_: Exception) {
                _state.update {
                    it.copy(error = LoginError.Unexpected)
                }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun handleAuthenticationError(
        exception: AuthenticationException,
    ) {
        val loginError = when (exception) {
            is AuthenticationException.Rejected -> LoginError.Rejected
            is AuthenticationException.InvalidResponse -> LoginError.InvalidResponse
        }

        _state.update {
            it.copy(error = loginError)
        }
    }
}
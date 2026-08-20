package com.jeremiascortes.loquetengo.feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager

@Composable
internal fun LoginRoute(
    authRepository: AuthRepository,
    sessionManager: AuthSessionManager,
) {
    val factory = remember(authRepository, sessionManager) {
        viewModelFactory {
            initializer {
                LoginViewModel(
                    authRepository = authRepository,
                    sessionManager = sessionManager,
                )
            }
        }
    }

    val viewModel = viewModel<LoginViewModel>(
        factory = factory,
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}
package com.jeremiascortes.loquetengo.feature.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RegisterRoute(
    // TODO: Falta la implementación del viewmodel
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateToBack = onNavigateToBack
    )
}
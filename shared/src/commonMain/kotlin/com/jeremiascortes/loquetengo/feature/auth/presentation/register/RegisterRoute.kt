package com.jeremiascortes.loquetengo.feature.auth.presentation.register

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import loquetengo.shared.generated.resources.Res
import loquetengo.shared.generated.resources.login_title
import loquetengo.shared.generated.resources.register_success_message
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RegisterRoute(
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val registerSuccessMessage = stringResource(Res.string.register_success_message)
    val loginActionLabel = stringResource(Res.string.login_title)

    LaunchedEffect(viewModel) {

        viewModel.events.collect { event ->
            when (event) {
                is RegisterEvent.ShowAccountCreatedMessage -> {
                    val result = snackbarHostState.showSnackbar(
                        message = registerSuccessMessage,
                        actionLabel = loginActionLabel,
                        duration = SnackbarDuration.Long,
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        onNavigateToBack()
                    }
                }
            }
        }
    }

    RegisterScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction,
        onNavigateToBack = onNavigateToBack
    )
}
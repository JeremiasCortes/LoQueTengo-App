package com.jeremiascortes.loquetengo.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import com.jeremiascortes.loquetengo.feature.auth.presentation.login.LoginRoute

@Composable
internal fun AuthNavHost(
    authRepository: AuthRepository,
    sessionManager: AuthSessionManager,
) {
    val backStack = rememberSerializable(
        serializer = SnapshotStateListSerializer<AuthRoute>(),
    ) {
        mutableStateListOf<AuthRoute>(AuthRoute.Login)
    }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLast()
            }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<AuthRoute.Login> {
                LoginRoute(
                    authRepository = authRepository,
                    sessionManager = sessionManager,
                )
            }
        },
    )
}
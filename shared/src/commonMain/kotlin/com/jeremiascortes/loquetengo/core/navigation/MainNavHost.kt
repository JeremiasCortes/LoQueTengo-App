package com.jeremiascortes.loquetengo.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import loquetengo.shared.generated.resources.Res
import loquetengo.shared.generated.resources.logout
import loquetengo.shared.generated.resources.main_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MainNavHost(
    onLogout: () -> Unit,
) {
    val backStack = rememberSerializable(
        serializer = SnapshotStateListSerializer<MainRoute>(),
    ) {
        mutableStateListOf<MainRoute>(MainRoute.Home)
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
        ),
        entryProvider = entryProvider {
            entry<MainRoute.Home> {
                MainPlaceholderScreen(
                    onLogout = onLogout,
                )
            }
        },
    )
}

@Composable
private fun MainPlaceholderScreen(
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(Res.string.main_placeholder))

        Button(onClick = onLogout) {
            Text(stringResource(Res.string.logout))
        }
    }
}
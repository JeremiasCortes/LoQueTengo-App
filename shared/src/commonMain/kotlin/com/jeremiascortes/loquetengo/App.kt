package com.jeremiascortes.loquetengo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeremiascortes.loquetengo.core.di.AppContainer
import com.jeremiascortes.loquetengo.core.navigation.AuthNavHost
import com.jeremiascortes.loquetengo.core.navigation.MainNavHost

@Composable
fun App(
    baseUrl: String = "http://localhost:8080/",
) {
    val container = remember(baseUrl) {
        AppContainer(baseUrl = baseUrl)
    }

    DisposableEffect(container) {
        onDispose {
            container.close()
        }
    }

    val session by container.sessionManager.session
        .collectAsStateWithLifecycle()

    MaterialTheme {
        if (session == null) {
            AuthNavHost(
                authRepository = container.authRepository,
                sessionManager = container.sessionManager,
            )
        } else {
            MainNavHost(
                onLogout = container.sessionManager::clearSession,
            )
        }
    }
}
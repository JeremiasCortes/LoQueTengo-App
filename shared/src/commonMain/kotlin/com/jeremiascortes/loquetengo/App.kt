package com.jeremiascortes.loquetengo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeremiascortes.loquetengo.core.di.createAppModule
import com.jeremiascortes.loquetengo.core.navigation.AuthNavHost
import com.jeremiascortes.loquetengo.core.navigation.MainNavHost
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration

@Composable
fun App(
    baseUrl: String = "http://localhost:8080/",
) {
    val configuration = remember(baseUrl) {
        koinConfiguration {
            modules(createAppModule(baseUrl))
        }
    }

    KoinApplication(
        configuration = configuration,
    ) {
        AppContent()
    }
}

@Composable
private fun AppContent(
    sessionManager: AuthSessionManager = koinInject(),
) {
    val session by sessionManager.session
        .collectAsStateWithLifecycle()

    MaterialTheme {
        if (session == null) {
            AuthNavHost()
        } else {
            MainNavHost(
                onLogout = sessionManager::clearSession,
            )
        }
    }
}
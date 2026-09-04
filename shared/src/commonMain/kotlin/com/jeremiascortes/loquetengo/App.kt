package com.jeremiascortes.loquetengo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jeremiascortes.loquetengo.core.di.createAppModule
import com.jeremiascortes.loquetengo.core.navigation.AuthNavHost
import com.jeremiascortes.loquetengo.core.navigation.MainNavHost
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import kotlinx.coroutines.launch
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
    val session by sessionManager.session.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    var restorationFinished by remember(sessionManager) {
        mutableStateOf(false)
    }

    LaunchedEffect(sessionManager) {
        try {
            sessionManager.restoreSession()
        } finally {
            restorationFinished = true
        }
    }

    MaterialTheme {
        when {
            !restorationFinished -> {
                SessionRestoringScreen()
            }

            session == null -> {
                AuthNavHost()
            }

            else -> {
                MainNavHost(
                    onLogout = {
                        coroutineScope.launch {
                            sessionManager.clearSession()
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun SessionRestoringScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}
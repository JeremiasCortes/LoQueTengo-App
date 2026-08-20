package com.jeremiascortes.loquetengo.feature.auth.domain.session

import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AuthSessionManager {

    private val _session = MutableStateFlow<AuthSession?>(null)

    val session: StateFlow<AuthSession?> = _session.asStateFlow()

    fun startSession(session: AuthSession) {
        _session.value = session
    }

    fun clearSession() {
        _session.value = null
    }
}
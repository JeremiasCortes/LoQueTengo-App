package com.jeremiascortes.loquetengo.feature.auth.domain.session

import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AuthSessionManager(
    private val sessionStorage: AuthSessionStorage,
) {

    private val _session = MutableStateFlow<AuthSession?>(null)

    val session: StateFlow<AuthSession?> = _session.asStateFlow()

    suspend fun restoreSession() {
        try {
            _session.value = sessionStorage.read()
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: Exception) {
            /*
             * Si el almacenamiento no puede leerse, arrancamos sin sesión
             * en lugar de impedir que la aplicación se abra.
             */
            _session.value = null
        }
    }

    suspend fun startSession(session: AuthSession) {
        /*
         * Guardamos primero. Solo publicamos la sesión si la persistencia
         * ha terminado correctamente.
         */
        sessionStorage.write(session)
        _session.value = session
    }

    suspend fun clearSession() {
        try {
            sessionStorage.clear()
        } finally {
            /*
             * Aunque falle el almacenamiento, el usuario debe salir
             * inmediatamente de la sesión que está en memoria.
             */
            _session.value = null
        }
    }
}
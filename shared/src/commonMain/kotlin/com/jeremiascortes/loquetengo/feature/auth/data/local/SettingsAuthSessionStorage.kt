package com.jeremiascortes.loquetengo.feature.auth.data.local

import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession
import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthUser
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionStorage
import com.russhwolf.settings.Settings
import kotlin.uuid.Uuid

internal class SettingsAuthSessionStorage(
    private val settings: Settings = Settings(),
) : AuthSessionStorage {

    override suspend fun read(): AuthSession? {
        val accessToken = settings.getStringOrNull(ACCESS_TOKEN_KEY)
            ?: return null

        val userId = settings.getStringOrNull(USER_ID_KEY)
            ?: return clearInvalidSession()

        val userEmail = settings.getStringOrNull(USER_EMAIL_KEY)
            ?: return clearInvalidSession()

        val parsedUserId = try {
            Uuid.parse(userId)
        } catch (_: IllegalArgumentException) {
            return clearInvalidSession()
        }

        return AuthSession(
            accessToken = accessToken,
            user = AuthUser(
                id = parsedUserId,
                email = userEmail,
            ),
        )
    }

    override suspend fun write(session: AuthSession) {
        /*
         * Guardamos primero los datos del usuario y el token al final.
         * La presencia del token indica que la sesión terminó de guardarse.
         */
        settings.putString(
            key = USER_ID_KEY,
            value = session.user.id.toString(),
        )

        settings.putString(
            key = USER_EMAIL_KEY,
            value = session.user.email,
        )

        settings.putString(
            key = ACCESS_TOKEN_KEY,
            value = session.accessToken,
        )
    }

    override suspend fun clear() {
        settings.remove(ACCESS_TOKEN_KEY)
        settings.remove(USER_ID_KEY)
        settings.remove(USER_EMAIL_KEY)
    }

    private suspend fun clearInvalidSession(): AuthSession? {
        clear()
        return null
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "auth.access_token"
        const val USER_ID_KEY = "auth.user_id"
        const val USER_EMAIL_KEY = "auth.user_email"
    }
}
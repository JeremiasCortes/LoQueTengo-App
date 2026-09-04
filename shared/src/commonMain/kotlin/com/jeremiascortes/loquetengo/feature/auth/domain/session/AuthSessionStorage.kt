package com.jeremiascortes.loquetengo.feature.auth.domain.session

import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession

internal interface AuthSessionStorage {

    suspend fun read(): AuthSession?

    suspend fun write(session: AuthSession)

    suspend fun clear()
}
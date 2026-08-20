package com.jeremiascortes.loquetengo.feature.auth.data.mapper

import com.jeremiascortes.loquetengo.feature.auth.data.remote.dto.LoginSessionDto
import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession
import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthUser

internal fun LoginSessionDto.toDomain(): AuthSession {
    return AuthSession(
        accessToken = token,
        user = AuthUser(
            id = user.id,
            email = user.email,
        ),
    )
}
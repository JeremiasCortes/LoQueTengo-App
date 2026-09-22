package com.jeremiascortes.loquetengo.feature.auth.data.mapper

import com.jeremiascortes.loquetengo.feature.auth.data.remote.dto.RegisterSessionDto
import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthUser

internal fun RegisterSessionDto.toDomain(): AuthUser {
    return AuthUser(
        id = id,
        email = email
    )
}
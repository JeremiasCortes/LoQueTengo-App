package com.jeremiascortes.loquetengo.feature.auth.domain.model

import kotlin.uuid.Uuid

data class AuthUser(
    val id: Uuid,
    val email: String
)

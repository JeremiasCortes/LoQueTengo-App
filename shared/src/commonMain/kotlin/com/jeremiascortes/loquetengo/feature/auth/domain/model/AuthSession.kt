package com.jeremiascortes.loquetengo.feature.auth.domain.model

data class AuthSession(
    val accessToken: String,
    val user: AuthUser
)

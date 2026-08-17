package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequestDto(
    val email: String,
    val password: String
)
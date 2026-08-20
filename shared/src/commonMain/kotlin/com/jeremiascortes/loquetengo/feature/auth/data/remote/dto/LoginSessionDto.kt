package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginSessionDto(
    val token: String,
    val user: LoginUserDto
)
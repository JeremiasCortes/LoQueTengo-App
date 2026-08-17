package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class LoginResponseDto(
    val success: Boolean,
    val data
)
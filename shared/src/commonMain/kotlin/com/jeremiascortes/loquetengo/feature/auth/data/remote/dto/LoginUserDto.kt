package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class LoginUserDto(
    val id: Uuid,
    val email: String
)
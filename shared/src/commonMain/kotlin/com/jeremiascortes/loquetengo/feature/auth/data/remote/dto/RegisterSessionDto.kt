package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

/**
 * Represents the session response data for a registered user the API.
 *
 * This data class contains a token that is used to keep the session
 * active, and a reference to the user information.
 *
 * @property id The user id.
 * @property email The user email.
 */
@Serializable
internal data class RegisterSessionDto(
    val id: Uuid,
    val email: String,
)

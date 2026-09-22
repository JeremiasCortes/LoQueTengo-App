package com.jeremiascortes.loquetengo.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Represents the session response data for a logged-in user the API.
 *
 * This data class contains a token that is used to keep the session
 * active, and a reference to the user information.
 *
 * @property token The token used for authentication.
 * @property user The user details associated with the session.
 */
@Serializable
internal data class LoginSessionDto(
    val token: String,
    val user: LoginUserDto
)
package com.jeremiascortes.loquetengo.feature.auth.domain.error

internal sealed class AuthenticationException(
    message: String? = null
): Exception(message) {

    class Rejected(
        serverMessage: String?
    ): AuthenticationException(serverMessage)

    class InvalidResponse: AuthenticationException()
}
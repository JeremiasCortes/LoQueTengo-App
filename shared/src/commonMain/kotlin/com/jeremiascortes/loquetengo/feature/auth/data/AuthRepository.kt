package com.jeremiascortes.loquetengo.feature.auth.data

import com.jeremiascortes.loquetengo.feature.auth.data.mapper.toDomain
import com.jeremiascortes.loquetengo.feature.auth.data.remote.AuthRemoteDataSource
import com.jeremiascortes.loquetengo.feature.auth.data.remote.dto.LoginRequestDto
import com.jeremiascortes.loquetengo.feature.auth.domain.error.AuthenticationException
import com.jeremiascortes.loquetengo.feature.auth.domain.model.AuthSession

internal class AuthRepository(
    private val remoteDataSource: AuthRemoteDataSource,
) {

    suspend fun login(
        email: String,
        password: String,
    ): AuthSession {
        val response = remoteDataSource.login(
            request = LoginRequestDto(
                email = email,
                password = password,
            ),
        )

        if (!response.success) {
            throw AuthenticationException.Rejected(
                serverMessage = response.message,
            )
        }

        val sessionDto = response.data
            ?: throw AuthenticationException.InvalidResponse()

        return sessionDto.toDomain()
    }
}
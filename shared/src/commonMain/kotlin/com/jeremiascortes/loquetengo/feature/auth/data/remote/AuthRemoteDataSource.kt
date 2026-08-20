package com.jeremiascortes.loquetengo.feature.auth.data.remote

import com.jeremiascortes.loquetengo.core.network.ApiResponseDto
import com.jeremiascortes.loquetengo.feature.auth.data.remote.dto.LoginRequestDto
import com.jeremiascortes.loquetengo.feature.auth.data.remote.dto.LoginSessionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AuthRemoteDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun login(
        request: LoginRequestDto,
    ): ApiResponseDto<LoginSessionDto> {
        return httpClient.post(LOGIN_PATH) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    private companion object {
        const val LOGIN_PATH = "api/v1/auth/login"
    }
}
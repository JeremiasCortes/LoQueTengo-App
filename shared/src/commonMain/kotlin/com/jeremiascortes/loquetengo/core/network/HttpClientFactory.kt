package com.jeremiascortes.loquetengo.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.Url
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal object HttpClientFactory {

    fun createHttpClient(
        baseUrl: String,
        accessTokenProvider: suspend () -> String?,
    ): HttpClient {
        val apiUrl = Url(baseUrl)

        return HttpClient {
            expectSuccess = true

            defaultRequest {
                url(baseUrl)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(Auth) {
                bearer {
                    // El token lo administra AuthSessionManager.
                    cacheTokens = false

                    loadTokens {
                        accessTokenProvider()?.let { token ->
                            BearerTokens(
                                accessToken = token,
                                refreshToken = null,
                            )
                        }
                    }

                    // Solo enviamos el token a nuestro backend y nunca al login.
                    sendWithoutRequest { request ->
                        request.url.host == apiUrl.host &&
                                request.url.encodedPath != "/api/v1/auth/login"
                    }
                }
            }
        }
    }
}
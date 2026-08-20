package com.jeremiascortes.loquetengo.core.di

import com.jeremiascortes.loquetengo.core.network.HttpClientFactory
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.data.remote.AuthRemoteDataSource
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager

internal class AppContainer(
    baseUrl: String,
) {

    private val httpClient = HttpClientFactory.createHttpClient(
        baseUrl = baseUrl,
    )

    val sessionManager = AuthSessionManager()

    private val authRemoteDataSource = AuthRemoteDataSource(
        httpClient = httpClient,
    )

    val authRepository = AuthRepository(
        remoteDataSource = authRemoteDataSource,
    )

    fun close() {
        httpClient.close()
    }
}
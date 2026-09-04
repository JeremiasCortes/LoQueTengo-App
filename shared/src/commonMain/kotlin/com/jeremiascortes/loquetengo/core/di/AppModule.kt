package com.jeremiascortes.loquetengo.core.di

import com.jeremiascortes.loquetengo.core.network.HttpClientFactory
import com.jeremiascortes.loquetengo.feature.auth.data.AuthRepository
import com.jeremiascortes.loquetengo.feature.auth.data.local.SettingsAuthSessionStorage
import com.jeremiascortes.loquetengo.feature.auth.data.remote.AuthRemoteDataSource
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionManager
import com.jeremiascortes.loquetengo.feature.auth.domain.session.AuthSessionStorage
import com.jeremiascortes.loquetengo.feature.auth.presentation.login.LoginViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.dsl.onClose

internal fun createAppModule(
    baseUrl: String,
): Module = module {

    /*
     * Almacenamiento persistente de la sesión.
     */
    single<AuthSessionStorage> {
        SettingsAuthSessionStorage()
    }

    /*
     * Una sola sesión para toda la aplicación.
     */
    singleOf(::AuthSessionManager)

    /*
     * Un único HttpClient compartido.
     *
     * Captura AuthSessionManager para obtener siempre el token actual.
     */
    single<HttpClient> {
        val sessionManager = get<AuthSessionManager>()

        HttpClientFactory.createHttpClient(
            baseUrl = baseUrl,
            accessTokenProvider = {
                sessionManager.session.value?.accessToken
            },
        )
    } onClose { httpClient ->
        httpClient?.close()
    }

    /*
     * Dependencias de la feature de autenticación.
     */
    singleOf(::AuthRemoteDataSource)
    singleOf(::AuthRepository)

    /*
     * Koin respeta el ciclo de vida del ViewModel.
     */
    viewModelOf(::LoginViewModel)
}
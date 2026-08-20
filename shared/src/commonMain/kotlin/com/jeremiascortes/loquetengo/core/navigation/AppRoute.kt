package com.jeremiascortes.loquetengo.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AuthRoute: NavKey {

    @Serializable
    data object Login: AuthRoute
}

@Serializable
internal sealed interface MainRoute: NavKey {
    @Serializable
    data object Home: MainRoute
}
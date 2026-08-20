package com.jeremiascortes.loquetengo.core.network

import kotlinx.serialization.Serializable

@Serializable
internal data class ApiResponseDto<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)
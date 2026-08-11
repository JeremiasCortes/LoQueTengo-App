package com.jeremiascortes.loquetengo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
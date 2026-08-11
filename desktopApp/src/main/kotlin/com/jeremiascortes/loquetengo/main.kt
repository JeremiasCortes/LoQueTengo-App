package com.jeremiascortes.loquetengo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LoQueTengo",
    ) {
        App()
    }
}
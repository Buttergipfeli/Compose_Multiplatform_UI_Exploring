package ch.ak

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose_Multiplatform_UI_Exploring",
    ) {
        App()
    }
}
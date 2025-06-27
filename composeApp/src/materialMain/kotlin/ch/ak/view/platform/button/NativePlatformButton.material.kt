package ch.ak.view.platform.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
actual fun NativePlatformButton(onClick: () -> Unit, text: String) {
    Button(onClick) {
        Text(text)
    }
}

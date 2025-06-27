package ch.ak.view.platform.button

import androidx.compose.runtime.Composable

@Composable
expect fun NativePlatformButton(
    onClick: () -> Unit,
    text: String
)

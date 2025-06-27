package ch.ak.view.platform.button

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformButton(
    onClick: () -> Unit,
    text: String
)

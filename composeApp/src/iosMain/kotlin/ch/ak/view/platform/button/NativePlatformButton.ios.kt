package ch.ak.view.platform.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import ch.ak.LocalNativeViewFactory

@Composable
actual fun NativePlatformButton(onClick: () -> Unit, text: String) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        factory = {
            factory.createNativePlatformButton(
                onClick = onClick,
                text = text
            )
        }
    )
}

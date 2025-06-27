package ch.ak.view.platform.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeSystem
import platform.UIKit.UIControlEventTouchUpInside
import platform.UIKit.UIControlStateNormal
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformButton(onClick: () -> Unit, text: String) {
    val handler = remember { ActionTarget(onClick) }

    UIKitView(
        factory = {
            UIButton.buttonWithType(UIButtonTypeSystem).apply {
                setTitle(text, forState = UIControlStateNormal)
                sizeToFit()
                addTarget(
                    target = handler,
                    action = NSSelectorFromString("trigger"),
                    forControlEvents = UIControlEventTouchUpInside
                )
            }
        },
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(60.dp)
    )
}

class ActionTarget(val onClick: () -> Unit) : NSObject() {
    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun trigger() {
        onClick()
    }
}

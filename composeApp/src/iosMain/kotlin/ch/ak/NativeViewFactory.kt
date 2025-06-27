package ch.ak

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun createNativePlatformButton(onClick: () -> Unit, text: String): UIViewController
}

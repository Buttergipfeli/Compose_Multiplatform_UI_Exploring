# Compose Multiplatform UI Exploring

A small project to explore the capabilities of Compose Multiplatform.

## Installation

### iOS Configuration in Android Studio

Only required if the iOS configuration is not available in the run configurations dropdown.

1. Install the **Kotlin Multiplatform** plugin.
2. Click on **Edit Configurations** in the dropdown where the `composeApp` run configuration for
   Android is located.
3. Click the **+** button to add a new configuration and select **iOS Application**.
4. Choose a name for the configuration, e.g., `composeApp`.
5. Under **Xcode project file**, select the file:  
   `iosApp/iosApp.xcodeproj/project.xcworkspace`.
6. Under **Execution target**, select an iOS simulator.
7. Now the iOS configuration should be available in the run configurations dropdown.

## Examples

### ViewModel

- [
  `composeApp/src/commonMain/kotlin/ch/ak/viewmodel/AppViewModel.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/commonMain/kotlin/ch/ak/viewmodel/AppViewModel.kt)
  is used in [
  `composeApp/src/commonMain/kotlin/ch/ak/App.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/commonMain/kotlin/ch/ak/App.kt).

### Platform-specific views

To implement platform-specific logic, `expect` and `actual` declarations are used.  
To avoid having to create a separate view for each platform individually (e.g., Android, Desktop),
and instead share a single implementation across multiple platforms (excluding iOS), you can define
a shared source set (e.g., `materialMain`) in your [
`build.gradle.kts`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/build.gradle.kts).  
This setup allows Android and Desktop to share the same view logic in `materialMain`, while iOS uses
its own source set (`iosMain`), both depending on `commonMain`.

Note: I have currently tested the implementation only on Android and iOS, so the desktop version may
not work as expected.  
Also note that platform-specific non-Material views must have a fixed size.

```kotlin
val commonMain by getting
val desktopMain by getting
val androidMain by getting

val materialMain by creating {
    dependsOn(commonMain)
}
androidMain.dependsOn(materialMain)
desktopMain.dependsOn(materialMain)

val iosX64Main by getting
val iosArm64Main by getting
val iosSimulatorArm64Main by getting
val iosMain by creating {
    dependsOn(commonMain)
}
iosX64Main.dependsOn(iosMain)
iosArm64Main.dependsOn(iosMain)
iosSimulatorArm64Main.dependsOn(iosMain)
```

#### Material and androidx.compose.ui.viewinterop.UIKitView views

In this setup, iOS uses `androidx.compose.ui.viewinterop.UIKitView` to integrate native UIKit views
within Compose.
On Android and all other non-iOS platforms, Material Design views are used.

##### Implementation example

1. Create an `expect fun` in `materialMain`, e.g: [
   `composeApp/src/commonMain/kotlin/ch/ak/view/platform/button/PlatformButton.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/commonMain/kotlin/ch/ak/view/platform/button/PlatformButton.kt)
2. Implement the `actual fun` for the non iOS targets in the source set `materialMain`, e.g: [
   `composeApp/src/materialMain/kotlin/ch/ak/view/platform/button/PlatformButton.material.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/materialMain/kotlin/ch/ak/view/platform/button/PlatformButton.material.kt)
3. Implement the `actual fun` for the iOS target in the source set `iosMain`, e.g: [
   `composeApp/src/iosMain/kotlin/ch/ak/view/platform/button/PlatformButton.ios.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/iosMain/kotlin/ch/ak/view/platform/button/PlatformButton.ios.kt)

The actual fun implementations must be in the same package as their corresponding expect fun
declarations in `commonMain`.
Also, be sure to add the source set name as a suffix to the file name, for example:
`PlatformButton.material.kt` for the `materialMain` source set.

#### Material and native SwiftUI views

In this setup, iOS uses native SwiftUI views, while Android and all other non-iOS platforms use Material Design views.
For reference, I've used this video from Philipp
Lackner: https://www.youtube.com/watch?v=F0BnN_uLp9A&ab_channel=PhilippLackner

##### Implementation example

1. Create an `expect fun` in `materialMain`, e.g.:  
   [
   `composeApp/src/commonMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/commonMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.kt)
2. Implement the `actual fun` for the non-iOS targets in the `materialMain` source set, e.g.:  
   [
   `composeApp/src/materialMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.material.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/materialMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.material.kt)
3. Create a `NativeViewFactory` interface in the `iosMain` source set, which defines a method
   returning a `UIViewController`, e.g.:  
   [
   `composeApp/src/iosMain/kotlin/ch/ak/NativeViewFactory.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/iosMain/kotlin/ch/ak/NativeViewFactory.kt)
4. Create a `CompositionLocal` for `NativeViewFactory` in `MainViewController` inside the `iosMain`
   source set, e.g.:  
   [
   `composeApp/src/iosMain/kotlin/ch/ak/MainViewController.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/iosMain/kotlin/ch/ak/MainViewController.kt)
5. Implement the `NativeViewFactory` interface in Swift by creating an `iOSNativeViewFactory` class
   in the `iosApp` directory, e.g.:  
   [
   `iosApp/iosApp/View/iOSNativeViewFactory.swift`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/iosApp/iosApp/View/iOSNativeViewFactory.swift)
6. Pass an instance of `iOSNativeViewFactory` to `MainViewControllerKt.MainViewController` using
   `ComposeView.makeUIViewController(...)` in `ContentView.swift`, e.g.:  
   [
   `iosApp/iosApp/ContentView.swift`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/iosApp/iosApp/ContentView.swift)
7. Write your native SwiftUI view and return it as a `UIViewController` instance from the
   `NativeViewFactory` implementation method, e.g.:  
   [
   `iosApp/iosApp/View/Button/NativePlatformButton.swift`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/iosApp/iosApp/View/Button/NativePlatformButton.swift)
8. In the `actual fun` implementation for iOS (inside `iosMain`), retrieve the `NativeViewFactory`
   via the `CompositionLocal`, call the factory method, and embed the returned SwiftUI view, e.g.:  
   [
   `composeApp/src/iosMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.ios.kt`](https://github.com/Buttergipfeli/Compose_Multiplatform_UI_Exploring/blob/main/composeApp/src/iosMain/kotlin/ch/ak/view/platform/button/NativePlatformButton.ios.kt)


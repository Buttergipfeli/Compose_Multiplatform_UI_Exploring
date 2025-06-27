# Compose Multiplatform UI Exploring

A small project to explore the capabilities of desktop applications using Compose Multiplatform.

## Installation

### iOS Configuration in Android Studio

1. Install the **Kotlin Multiplatform** plugin.
2. Click on **Edit Configurations** in the dropdown where the `composeApp` run configuration for Android is located.
3. Click the **+** button to add a new configuration and select **iOS Application**.
4. Choose a name for the configuration, e.g., `composeApp`.
5. Under **Xcode project file**, select the file:  
   `iosApp/iosApp.xcodeproj/project.xcworkspace`.
6. Under **Execution target**, select an iOS simulator.
7. Now the iOS configuration should be available in the run configurations dropdown.

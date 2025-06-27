package ch.ak

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.ak.view.platform.button.NativePlatformButton
import ch.ak.view.platform.button.PlatformButton
import ch.ak.viewmodel.AppViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import compose_multiplatform_ui_exploring.composeapp.generated.resources.Res
import compose_multiplatform_ui_exploring.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    val viewModel = remember { AppViewModel() }
    val isShowingContent by viewModel.isShowingContent.collectAsState()

    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = viewModel::toggleContentVisibility) {
                Text("Click me!")
            }
            PlatformButton(
                onClick = viewModel::toggleContentVisibility,
                text = "Click me (platform button)!"
            )
            NativePlatformButton(
                onClick = viewModel::toggleContentVisibility,
                text = "Click me (native platform button)!"
            )

            AnimatedVisibility(isShowingContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

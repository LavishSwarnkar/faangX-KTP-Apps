package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun MiniApp(
    modifier: Modifier = Modifier.fillMaxSize(),
    title: String,
    composable: @Composable () -> Unit
) {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = title,
            state = rememberWindowState(
                placement = WindowPlacement.Fullscreen,
                size = DpSize(2000.dp, 2000.dp)
            )
        ) {

            Column (
                modifier = modifier.padding(16.dp)
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(12.dp),
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                composable()
            }
        }
    }
}
package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TowersOfHanoi",
            state = rememberWindowState(
//                placement = WindowPlacement.Fullscreen,
                size = DpSize(352.dp, 800.dp)
            )
        ) {
            Rod()
        }
    }
}

@Composable
fun Rod() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(12.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Disc(0.5f)
        Disc(0.75f)
        Disc(1f)
    }
}

@Composable
fun Disc(widthFraction: Float) {
    Box(
        modifier = Modifier.fillMaxWidth(widthFraction)
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    )
}

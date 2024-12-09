package com.faangx.ktp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
                size = DpSize(1000.dp, 700.dp)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                Rod("A", 3)
                Rod("B", 6)
                Rod("C", 9)
            }
        }
    }
}

@Composable
fun RowScope.Rod(label: String, noOfDiscs: Int) {

    val widthFractionStart = 0.2f

    val step = (1 - widthFractionStart) / noOfDiscs

    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(12.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(noOfDiscs) {
            Disc(widthFractionStart + ((it + 1) * step))
        }

        Text(
            text = label,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
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

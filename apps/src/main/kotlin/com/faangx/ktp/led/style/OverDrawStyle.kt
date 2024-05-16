package com.faangx.ktp.led.style

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.DpSize
import com.faangx.ktp.led.Coordinate
import com.faangx.ktp.led.LedDisplayAppConfig
import com.faangx.ktp.led.LedDisplayAppFunctionality

@Composable
fun LedDisplayOverDrawStyle(
    height: Int,
    width: Int,
    usableSize: DpSize,
    functionality: LedDisplayAppFunctionality
) {
    val radius = LedDisplayAppConfig.BOX_SIZE / 2

    val flow = remember { functionality.getCoordinateFlow(height, width) }
    val coordinates = mutableStateListOf<Coordinate>()

    LaunchedEffect(Unit) {
        val initial = Coordinate.circle(radius)
        flow.collect {
            if (it == initial) coordinates.clear()
            coordinates.add(it)
        }
    }

    MaterialTheme {

        Box(
            Modifier.fillMaxSize()
                .background(LedDisplayAppConfig.backgroundColor)
        ) {
            Canvas(
                modifier = Modifier.size(usableSize)
                    .align(Alignment.Center)

            ) {

                coordinates.forEach { coordinate ->
                    drawCircle(
                        color = LedDisplayAppConfig.objectColor,
                        radius = radius.toFloat(),
                        center = Offset(coordinate.x.toFloat(), coordinate.y.toFloat())
                    )
                }
            }
        }
    }
}
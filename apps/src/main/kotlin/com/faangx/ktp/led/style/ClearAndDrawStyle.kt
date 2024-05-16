package com.faangx.ktp.led.style

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.DpSize
import com.faangx.ktp.led.Coordinate
import com.faangx.ktp.led.LedDisplayAppConfig
import com.faangx.ktp.led.LedDisplayAppFunctionality

@Composable
fun LedDisplayClearAndDrawStyle(
    height: Int,
    width: Int,
    usableSize: DpSize,
    functionality: LedDisplayAppFunctionality,
    style: LedDisplayAppStyle
) {
    val radius = LedDisplayAppConfig.BOX_SIZE / 2

    MaterialTheme {
        val flow = remember { functionality.getCoordinateFlow(height, width) }
        val onLed = flow.collectAsState(Coordinate.circle(radius))
        val x = animateIntAsState(onLed.value.x)
        val y = animateIntAsState(onLed.value.y)

        Box(
            Modifier.fillMaxSize()
                .background(LedDisplayAppConfig.backgroundColor)
        ) {
            Canvas(
                modifier = Modifier.size(usableSize)
                    .align(Alignment.Center)

            ) {

                drawCircle(
                    color = LedDisplayAppConfig.objectColor,
                    radius = radius.toFloat(),
                    center = Offset(x.value.toFloat(), y.value.toFloat())
                )
            }
        }
    }
}
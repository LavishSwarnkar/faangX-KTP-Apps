package com.faangx.ktp.led

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.faangx.ktp.led.Phase.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.awt.Toolkit

fun main() {
    ledDisplayApp()
}

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Zero = Coordinate(0, 0)
    }
    fun up() = Coordinate(x, y - 1)
    fun down() = Coordinate(x, y + 1)
    fun right() = Coordinate(x + 1, y)
    fun left() = Coordinate(x - 1, y)
}

enum class Phase {
    Right, Down, Left, Up
}

fun getFlow(rows: Int, cols: Int): Flow<Coordinate> {
    var current = Coordinate.Zero
    var phase = Right

    return flow {
        while (true) {
            emit(current)
            delay(30)

            when (phase) {
                Right -> {
                    current = current.right()
                    if (current.x == cols - 1) phase = Down
                }
                Down -> {
                    current = current.down()
                    if (current.y == rows - 1) phase = Left
                }
                Left -> {
                    current = current.left()
                    if (current.x == 0) phase = Up
                }
                Up -> {
                    current = current.up()
                    if (current.y == 0) phase = Right
                }
            }
        }
    }
}

@Composable
private fun AppScreen(boxSize: Float, rows: Int, cols: Int) {
    val onColor = Color.White
    val offColor = Color(0xFF1c1c1c)

    val radius = boxSize / 2

    MaterialTheme {
        val flow = remember { getFlow(rows, cols) }
        val onLed = flow.collectAsState(Coordinate.Zero)
        val x = animateFloatAsState(onLed.value.x * boxSize)
        val y = animateFloatAsState(onLed.value.y * boxSize)

        Canvas(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black)
        ) {

            drawCircle(
                color = onColor,
                radius = radius,
                center = Offset(x.value + radius, y.value + radius)
            )
        }
    }
}

fun ledDisplayApp() = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)

    val boxSize = 200f

    val (rows, cols) = with(LocalDensity.current) {
        val size = Toolkit.getDefaultToolkit().screenSize
        val h = (size.height - 56) * density
        val w = size.width * density
        (h / boxSize).toInt() to (w / boxSize).toInt()
    }

    Window(onCloseRequest = ::exitApplication, state = state) {
        AppScreen(boxSize, rows, cols)
    }
}
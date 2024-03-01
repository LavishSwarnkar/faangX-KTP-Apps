package com.faangx.ktp.basics.loops

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.faangx.ktp.basics.loops.CircleCenter.*
import com.faangx.ktp.basics.loops.Direction.*
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

fun main() {
    goldenRatioApp()
}

fun goldenRatioApp() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Maximized,
        width = 1312.dp,
        height = 818.dp
    )

    LaunchedEffect(state.size) {
        println("size = (${state.size})")
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "The Golden Ratio",
        state = state
    ) {
        GoldenRatioSpiral()
    }
}

enum class Direction {
    Left, Down, Right, Up;

    fun next() = when (this) {
        Left -> Down
        Down -> Right
        Right -> Up
        Up -> Left
    }
}

enum class CircleCenter {
    BottomRight, BottomLeft, TopRight, TopLeft;

    companion object {
        fun forDirection(direction: Direction): CircleCenter {
            return when (direction) {
                Left -> BottomLeft
                Down -> BottomRight
                Right -> TopRight
                Up -> TopLeft
            }
        }
    }
}

@Composable
fun GoldenRatioSpiral() {
    val scale = 100f
//    val list = listOf(1, 1, 2, 3, 3, 2, 1, 1, 2, 3, 3, 2, 1, 1, 5, 6, 6, 5, 1, 1, 5, 6, 6, 5)
    val list = listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597)
//    val list = listOf(1, 1)
    var pointer = Offset(0f, 0f)
    var direction = Left
    var prevNum = list.first()
    var angle = 0f

    var mainScale by remember { mutableStateOf(10f) }

    LaunchedEffect(Unit) { delay(1); mainScale = 0.25f }

    val animatedMainScale = animateFloatAsState(
        mainScale,
        animationSpec = keyframes {
            var delay = 700f
            var duration = 0f
            for (i in (9 downTo 1).toList() + listOf(0.75f, 0.5f, 0.25f)) {
                i.toFloat() at delay.toInt()
                duration += delay
                delay *= 1.5f
            }
            durationMillis = duration.roundToInt()
        }
    )

    var rotation by remember { mutableStateOf(0f) }
    val animatedRotation = animateFloatAsState(rotation)
    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            rotation += 1
        }
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xff570bb7))
//            .scale(mainScale)
            .scale(animatedMainScale.value)
            .rotate(animatedRotation.value)
    ) {
        inset(left = size.width / 2f - 50f, top = size.height / 2f - 50f, right = 0f, bottom = 0f) {

            list.forEachIndexed { index, num ->
                if (index != 0) {
                    pointer = when (direction) {
                        Left -> Offset(pointer.x - num, pointer.y)
                        Down -> Offset(pointer.x, pointer.y + prevNum)
                        Right -> Offset(pointer.x + prevNum, pointer.y - (num - prevNum))
                        Up -> Offset(pointer.x - (num - prevNum), pointer.y - num)
                    }

                    direction = direction.next()
                    prevNum = num
                }

                val size = Size(num * scale, num * scale)

                drawRect(
                    color = Color.White,
                    size = size,
                    topLeft = pointer.scale(scale),
                    style = Stroke(width = 1f)
                )

                drawArc(
                    color = Color.White,
                    startAngle = angle,
                    sweepAngle = -90f,
                    useCenter = false,
                    topLeft = arcOffsetForCircleCenter(
                        Companion.forDirection(direction),
                        pointer.scale(scale),
                        num * scale
                    ),
                    size = Size(size.width * 2, size.height * 2),
                    style = Stroke(8f)
                )

                angle -= 90
            }
        }
    }
}

fun Offset.scale(scale: Float): Offset {
    return Offset(x * scale, y * scale)
}

fun arcOffsetForCircleCenter(center: CircleCenter, offset: Offset, size: Float): Offset {
    return when (center) {
        BottomLeft -> Offset(offset.x - size, offset.y)
        BottomRight -> Offset(offset.x, offset.y)
        TopRight -> Offset(offset.x, offset.y - size)
        TopLeft -> Offset(offset.x - size, offset.y - size)
    }
}
package com.faangx.ktp.basics.loops

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.faangx.ktp.basics.loops.CircleCenter.*
import com.faangx.ktp.basics.loops.Direction.*
import kotlinx.coroutines.delay

fun main() {
    goldenRatioApp()
}

fun goldenRatioApp() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Maximized,
        width = 1312.dp,
        height = 818.dp
    )

//    LaunchedEffect(state.size) {
//        println("size = (${state.size})")
//    }

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
    var scale by remember { mutableStateOf(300f) }
    val animatedScale = animateFloatAsState(scale)

    val list = listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597)
    var pointer = Offset(0f, 0f)
    var direction = Left
    var prevNum = list.first()
    var angle = 0f

    var rotateDegree by remember { mutableStateOf(0f) }

    LaunchedEffect(rotateDegree) {
        delay(10)
        rotateDegree -= 0.1f
    }

    LaunchedEffect(scale) {
        delay((200 - scale).toLong())
        if (scale != 2f) scale -= 1f
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
                .background(Color(0xff570bb7))
        ) {
            rotate(rotateDegree) {
                inset(left = size.width / 2f, top = size.height / 2f, right = 0f, bottom = 0f) {

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

                        val size = Size(num * animatedScale.value, num * animatedScale.value)

                        drawRect(
                            color = Color.White,
                            size = size,
                            topLeft = pointer.scale(animatedScale.value),
                            style = Stroke(width = 0.2f)
                        )

                        drawArc(
                            color = Color.White,
                            startAngle = angle,
                            sweepAngle = -90f,
                            useCenter = false,
                            topLeft = arcOffsetForCircleCenter(
                                Companion.forDirection(direction),
                                pointer.scale(animatedScale.value),
                                num * animatedScale.value
                            ),
                            size = Size(size.width * 2, size.height * 2),
                            style = Stroke(4f)
                        )

                        angle -= 90
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(36.dp)
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                onClick = {
                    if (scale != 2f) scale -= 5f
                }
            ) {
                Text(
                    text = "-",
                    color = Color.White,
                    style = MaterialTheme.typography.h3
                )
            }

            Text(
                modifier = Modifier.width(100.dp),
                text = "${scale.toInt()}",
                color = Color.White,
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = { scale += 5f }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    style = MaterialTheme.typography.h3
                )
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
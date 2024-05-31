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
import com.faangx.ktp.util.captureStdOutput
import kotlinx.coroutines.delay
import ksp.MiniApp
import kotlin.math.roundToInt

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

class SpiralConfig(
    val boxSize: Float = 100f,
    val radii: List<Int>,
    val centerOffset: Offset = Offset.Zero,
    val scale: Float = 10f,
    val scaleAnimation: Boolean = true,
    val scaleAnimationInitialDelay: Float = 300f,
    val scaleAnimationIncreaseFactor: Float = 1.5f,
    val rotateAnimation: Boolean = true,
    val rotateAnimationDelta: Int = 1, // -1 ACW, 1 CW
    val rotateAnimationDelay: Long = 3,
    val showBoxes: Boolean = false
)

fun SpiralFullScreenMiniApp(
    printFibonacciSeries: (Int) -> Unit
) {
    application {
        val state = rememberWindowState(
            placement = WindowPlacement.Maximized
        )
        Window(::exitApplication, state, title = "Golden Ratio Spiral") {

            SpiralApp(printFibonacciSeries)
        }
    }
}

@MiniApp(
    "Spiral",
    "ProgrammingFundamentals/Ep4/Spiral",
    "n",
    false
)
@Composable
fun SpiralApp(
    printFibonacciSeries: (Int) -> Unit
) {
    val series = captureStdOutput {
        printFibonacciSeries(250)
    }.split(", ").dropLast(1)
        .map { it.toLong().toInt() }

    Spiral(
        SpiralConfig(
            radii = series,
            centerOffset = Offset(-25f, 0f),
            boxSize = 25f,
            showBoxes = false
        )
//        SpiralConfig(
//            radii = series,
//            centerOffset = Offset(-25f, 0f),
//            rotateAnimation = false,
//            scaleAnimation = false,
//            scale = 1f,
//            boxSize = 25f,
//            showBoxes = false
//        )
    )
}

@Composable
fun Spiral(
    config: SpiralConfig
) {
    var draw by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(800); draw = true }

    val scale = config.boxSize
    var pointer = Offset(0f, 0f)
    var direction = Down
    var prevNum = config.radii.first()
    var angle = -90f

    var mainScale by remember { mutableStateOf(config.scale) }

    val animatedMainScale = if (config.scaleAnimation) {
        LaunchedEffect(Unit) { delay(800); mainScale = 0.25f }

        animateFloatAsState(
            mainScale,
            animationSpec = keyframes {
                var delay = config.scaleAnimationInitialDelay
                var duration = 0f
                for (i in ((config.scale - 1).toInt() downTo 1).toList() + listOf(0.75f, 0.5f, 0.25f)) {
                    i.toFloat() at delay.toInt()
                    duration += delay
                    delay *= config.scaleAnimationIncreaseFactor
                }
                durationMillis = duration.roundToInt()
            }
        )
    } else null


    var rotation by remember { mutableStateOf(0f) }

    val animatedRotation = if (config.rotateAnimation) {
        LaunchedEffect(Unit) {
            delay(800)
            while (true) {
                delay(config.rotateAnimationDelay)
                rotation += config.rotateAnimationDelta
            }
        }

        animateFloatAsState(rotation)
    } else null

    if (draw) {
        Canvas(
            modifier = Modifier.fillMaxSize()
                .background(Color(0xff570bb7))
                .scale(animatedMainScale?.value ?: mainScale)
                .rotate(animatedRotation?.value ?: rotation)
        ) {

            inset(
                left = size.width / 2f + config.centerOffset.x,
                top = size.height / 2f + config.centerOffset.y,
                right = 0f,
                bottom = 0f
            ) {

                config.radii.forEachIndexed { index, num ->
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

                    if (config.showBoxes) {
                        drawRect(
                            color = Color.White,
                            size = size,
                            topLeft = pointer.scale(scale),
                            style = Stroke(width = 1f)
                        )
                    }

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
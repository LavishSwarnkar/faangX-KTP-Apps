package com.faangx.ktp.led.functionality

import com.faangx.ktp.led.Coordinate
import com.faangx.ktp.led.LedDisplayAppConfig
import com.faangx.ktp.led.LedDisplayAppFunctionality
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

enum class Phase {
    Right, Down, Left, Up
}

val rectangleLoop = LedDisplayAppFunctionality { height, width ->

    val radius = LedDisplayAppConfig.BOX_SIZE / 2

    var current = Coordinate.circle(radius)
    var phase = Phase.Right

    val xExtreme =
        ((width / radius) * radius) - radius
    val yExtreme =
        ((height / radius) * radius) - radius

    flow {
        while (true) {
            emit(current)
            delay(LedDisplayAppConfig.ANIMATION_DELAY)

            when (phase) {
                Phase.Right -> {
                    current = current.right()
                    if (current.x == xExtreme) phase = Phase.Down
                }
                Phase.Down -> {
                    current = current.down()
                    if (current.y == yExtreme) phase = Phase.Left
                }
                Phase.Left -> {
                    current = current.left()
                    if (current.x == radius) phase = Phase.Up
                }
                Phase.Up -> {
                    current = current.up()
                    if (current.y == radius) phase = Phase.Right
                }
            }
        }
    }
}
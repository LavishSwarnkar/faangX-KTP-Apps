package com.faangx.ktp.led

import com.faangx.ktp.led.LedDisplayAppConfig.DISPLACEMENT

data class Coordinate(
    val x: Int = 0,
    val y: Int = 0,
    val displacement: Int = DISPLACEMENT
) {
    companion object {
        fun circle(radius: Int) = Coordinate(x = radius, y = radius)
    }

    fun up() = copy(y = y - displacement)
    fun down() = copy(y = y + displacement)
    fun right() = copy(x = x + displacement)
    fun left() = copy(x = x - displacement)
}
package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import com.faangx.ktp.basics.Spiral
import com.faangx.ktp.basics.SpiralConfig
import com.faangx.ktp.basics.SpiralFullScreenMiniApp
import com.faangx.ktp.basics.SpiralMiniApp

fun printFibonacciSeries(n: Int) {
    var a = 1L
    var b = 1L

    repeat(n) {
        if (it <= 1) {
            print("1, ")
        } else {
            val sum = a + b
            print("$sum, ")
            a = b
            b = sum
        }
    }
}

fun printNormalSeries(n: Int) {
    repeat(n) {
        print("${it + 1}, ")
    }
}

fun main() {
    SpiralMiniApp(
//    SpiralFullScreenMiniApp(
//        ::printNormalSeries,
        ::printFibonacciSeries
    )
}

@Composable
fun SpiralAppDemo() {
    Spiral(
        SpiralConfig(
            radii = listOf(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597),
            centerOffset = Offset(-100f, -100f)
        )
    )
}
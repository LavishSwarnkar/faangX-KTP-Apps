package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.MiniApp
import com.faangx.ktp.basics.SquareOfNumApp
import com.faangx.ktp.basics.SquareOfNumAppV1

fun square(x: Int): Int {
    return x * x
}

fun square(x: Long) = x * x * x

fun main() {
    MiniApp(title = "Square of Num") {
        SquareOfNumAppDemo()
    }
}

@Composable
fun SquareOfNumAppDemo() {
    SquareOfNumApp(::square)
}

@Composable
fun SquareOfNumAppV1Demo() {
    SquareOfNumAppV1(::square)
}
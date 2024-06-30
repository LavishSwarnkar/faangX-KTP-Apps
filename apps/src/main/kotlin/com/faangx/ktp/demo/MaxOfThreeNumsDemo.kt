package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfThreeNumsApp
import com.faangx.ktp.basics.MaxOfThreeNumsMiniApp

fun maxOf(x: Int, y: Int, z: Int): Int {
    if (x > y && x > z) {
        return x
    } else if (y > x && y > z) {
        return y
    } else {
        return z
    }
}

fun main() {
    MaxOfThreeNumsMiniApp(::maxOf)
}

@Composable
fun MaxOfThreeNumsDemo() {
    MaxOfThreeNumsApp(::maxOf)
}
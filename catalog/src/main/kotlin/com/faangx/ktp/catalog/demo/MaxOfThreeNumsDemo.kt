package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfThreeNumsApp

fun maxOf(x: Int, y: Int, z: Int): Int {
    if (x > y && x > z) {
        return x
    } else if (y > x && y > z) {
        return y
    } else {
        return z
    }
}

@Composable
fun MaxOfThreeNumsDemo() {
    MaxOfThreeNumsApp(::maxOf)
}
package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfTwoNumsApp

fun maxOf(x: Int, y: Int): Int {
    if (x > y) {
        return x
    } else {
        return y
    }
}

@Composable
fun MaxOfTwoNumsDemo() {
    MaxOfTwoNumsApp(::maxOf)
}
package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.MaxOfTwoNumsApp
import com.faangx.ktp.basics.MaxOfTwoNumsMiniApp

fun maxOf(x: Int, y: Int): Int {
    return if (x > y) x else y
}

fun main() {
    MaxOfTwoNumsMiniApp(::maxOf)
}

@Composable
fun MaxOfTwoNumsDemo() {
    MaxOfTwoNumsApp(::maxOf)
}
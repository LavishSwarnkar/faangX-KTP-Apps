package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SpiralApp

fun printFibonacciSeries(n: Int) {
    var current = 1L
    var next = 1L
    repeat(n) {
        print("$current, ")
        val sum = current + next
        current = next
        next = sum
    }
}

@Composable
fun SpiralDemo() {
    SpiralApp(
        printFibonacciSeries = ::printFibonacciSeries
    )
}

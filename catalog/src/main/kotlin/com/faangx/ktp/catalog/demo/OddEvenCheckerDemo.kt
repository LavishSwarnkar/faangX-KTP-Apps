package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.OddEvenChecker

fun checkEvenOdd(num: Int): String {
    if (num % 2 == 0) {
        return "Even"
    } else {
        return "Odd"
    }
}

@Composable
fun OddEvenCheckerDemo() {
    OddEvenChecker(::checkEvenOdd)
}
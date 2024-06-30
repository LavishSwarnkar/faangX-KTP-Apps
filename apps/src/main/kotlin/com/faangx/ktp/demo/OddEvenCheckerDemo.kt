package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.OddEvenChecker
import com.faangx.ktp.basics.OddEvenCheckerMiniApp

fun checkEvenOdd(num: Int): String {
    if (num % 2 == 0) {
        return "Even"
    } else {
        return "Odd"
    }
}

fun main() {
    OddEvenCheckerMiniApp(::checkEvenOdd)
}

@Composable
fun OddEvenCheckerDemo() {
    OddEvenChecker(::checkEvenOdd)
}
package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.LcmHcfCalculator
import com.faangx.ktp.basics.LcmHcfCalculatorMiniApp

fun lcmOf(x: Int, y: Int): Int {
    if (x == 0 || y == 0) {
        return 0
    }
    var lcm = if (x > y) x else y
    while (true) {
        if (lcm % x == 0 && lcm % y == 0) {
            return lcm
        }
        ++lcm
    }
}

fun hcfOf(x: Int, y: Int): Int {
    var a = x
    var b = y

    while (b != 0) {
        val temp = b
        b = a % b
        a = temp
    }

    return a
}

fun main() {
    LcmHcfCalculatorMiniApp(
        ::lcmOf,
        ::hcfOf
    )
}

@Composable
fun LcmHcfCalculatorDemo() {
    LcmHcfCalculator(
        getHCF = ::hcfOf,
        getLCM = ::lcmOf
    )
}
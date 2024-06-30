package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.CharFrequencyCalculator
import com.faangx.ktp.basics.CharFrequencyCalculatorMiniApp

fun calcCharFrequency(text: String): Map<Char, Int> {
    return text.groupBy { it }
        .mapValues { it.value.size }
}

fun main() {
    CharFrequencyCalculatorMiniApp(::calcCharFrequency)
}

@Composable
fun CharFrequencyCalculatorDemo() {
    CharFrequencyCalculator(::calcCharFrequency)
}
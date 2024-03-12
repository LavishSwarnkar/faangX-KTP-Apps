package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.GradeCalculatorApp

@Composable
fun GradeCalculatorDemo() {
    GradeCalculatorApp { m1, m2, m3, m4, m5 ->
        val average = (m1 + m2 + m3 + m4 + m5) / 50f
        when (average) {
            in 9f..10f -> "A+"
            in 8f..9f -> "A"
            in 7f..8f -> "B"
            in 6f..7f -> "C"
            in 5f..6f -> "D"
            in 3f..5f -> "E"
            else -> "Fail"
        }
    }
}
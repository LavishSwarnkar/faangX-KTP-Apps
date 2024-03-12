package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SimpleInterestCalculator

@Composable
fun SimpleInterestCalculatorDemo() {
    SimpleInterestCalculator() { p, r, t ->
        p * r * t / 100
    }
}
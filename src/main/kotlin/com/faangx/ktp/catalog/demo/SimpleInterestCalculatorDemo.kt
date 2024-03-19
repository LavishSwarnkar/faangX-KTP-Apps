package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SimpleInterestCalculator
import com.faangx.ktp.basics.SimpleInterestCalculatorV1

@Composable
fun SimpleInterestCalculatorDemo() {
    SimpleInterestCalculator { p, r, t ->
        p * r * t / 100
    }
}

@Composable
fun SimpleInterestCalculatorV1Demo() {
    SimpleInterestCalculatorV1 { p, r, t ->
        p * r * t / 100
    }
}
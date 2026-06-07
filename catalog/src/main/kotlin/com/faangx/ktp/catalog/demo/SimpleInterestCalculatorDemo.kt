package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.SimpleInterestCalculator
import com.faangx.ktp.basics.SimpleInterestCalculatorV1

fun getInterest(p: Int, r: Int, t: Int): Int {
    return p * r * t / 100
}

@Composable
fun SimpleInterestCalculatorDemo() {
    SimpleInterestCalculator(::getInterest)
}

@Composable
fun SimpleInterestCalculatorV1Demo() {
    SimpleInterestCalculator { p, r, t ->
        p * r * t / 100
    }
}
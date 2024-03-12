package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.FactorCalculator

@Composable
fun FactorCalculatorDemo() {
    val getFactorsOf = { x: Int ->
        (1..x).filter { x % it == 0 }
    }

    FactorCalculator(
        getFactorsOf = getFactorsOf,
        isPrime = { getFactorsOf(it).size == 2 }
    )
}
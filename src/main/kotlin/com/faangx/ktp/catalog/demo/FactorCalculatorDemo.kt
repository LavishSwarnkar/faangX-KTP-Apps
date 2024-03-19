package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.FactorCalculator
import com.faangx.ktp.basics.FactorCalculatorV1
import com.faangx.ktp.util.print

@Composable
fun FactorCalculatorDemo() {
    val getFactorsOf = { x: Int ->
        (1..x).filter { x % it == 0 }
    }

    FactorCalculator(
        printFactorsOf = { x ->
            for (i in 1..x) {
                if (x % i == 0) print("$i, ")
            }
        },
        isPrime = { getFactorsOf(it).size == 2 }
    )
}

@Composable
fun FactorCalculatorV1Demo() {
    val getFactorsOf = { x: Int ->
        (1..x).filter { x % it == 0 }
    }

    FactorCalculatorV1(
        getFactorsOf = getFactorsOf,
        isPrime = { getFactorsOf(it).size == 2 }
    )
}
package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.FactorCalculator
import com.faangx.ktp.basics.FactorCalculatorV1
import com.faangx.ktp.util.print

fun FactorCalculator.printFactorsOf(num: Int) {
    for (i in 1..num) {
        if (num % i == 0) {
            print("$i, ")
        }
    }
}

fun getFactorsOf(num: Int): List<Int> {
    return buildList {
        for (i in 1..num) {
            if (num % i == 0) {
                add(i)
            }
        }
    }
}

@Composable
fun FactorCalculatorDemo() {
    FactorCalculator(
        printFactorsOf = { printFactorsOf(it) },
        isPrime = { getFactorsOf(it).size == 2 }
    )
}

@Composable
fun FactorCalculatorV1Demo() {
    FactorCalculatorV1(
        getFactorsOf = ::getFactorsOf,
        isPrime = { getFactorsOf(it).size == 2 }
    )
}
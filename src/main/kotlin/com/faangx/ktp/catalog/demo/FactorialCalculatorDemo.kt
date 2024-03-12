package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.FactorialCalculatorApp

@Composable
fun FactorialCalculatorDemo() {
    val factorialOf = { x: Long ->
        (2L..x).fold(1L) { acc, i -> acc * i }
    }

    val permutationsOf = { n: Long, r: Long ->
        ((n - r + 1)..n).fold(1L) { acc, i -> acc * i }
    }

    FactorialCalculatorApp(
        factorialOf = factorialOf,
        permutationsOf = permutationsOf,
        combinationsOf = { n, r ->
            permutationsOf(n, r) / factorialOf(r)
        }
    )
}
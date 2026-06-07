package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.FactorialCalculatorApp

fun factorialOf(x: Long): Long {
    var factorial = 1L
    for (i in 2L..x) {
        factorial *= i
    }
    return factorial
}

fun factorialOf1(x: Long): Long {
    if (x == 1L) return 1
    return x * factorialOf1(x - 1)
}

fun factorialOf2(x: Long): Long {
    return (2L..x).fold(1L) { acc, i -> acc * i }
}

fun permutationsOf(n: Long, r: Long): Long {
    var permutations = 1L
    for (i in (n - r + 1)..n) {
        permutations *= i
    }
    return permutations
}

fun permutationsOf1(n: Long, r: Long): Long {
    return ((n - r + 1)..n).fold(1L) { acc, i -> acc * i }
}

fun combinationsOf(n: Long, r: Long): Long {
    return permutationsOf(n, r) / factorialOf(r)
}

@Composable
fun FactorialCalculatorDemo() {
    FactorialCalculatorApp(
        factorialOf = ::factorialOf,
        permutationsOf = ::permutationsOf,
        combinationsOf = ::combinationsOf
    )
}
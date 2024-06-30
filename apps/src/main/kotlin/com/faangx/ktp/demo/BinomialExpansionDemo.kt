package com.faangx.ktp.demo

import com.faangx.ktp.basics.BinomialExpansionMiniApp
import com.faangx.ktp.util.captureStdOutput

fun printBinomialExpansion(n: Int) {
    repeat(n + 1) { i ->
        val coefficient = combinationsOf(n, i)
        if (coefficient > 1) print(coefficient)

        val powerOfA = n - i
        if (powerOfA > 0) print("a")
        if (powerOfA > 1) print("^$powerOfA")

        if (i > 0) print("b")
        if (i > 1) print("^$i")

        if (i != n) print(" + ")
    }
}

fun factorialOf(x: Int): Int {
    var factorial = 1
    for (i in 2..x) {
        factorial *= i
    }
    return factorial
}

fun permutationsOf(n: Int, r: Int): Int {
    var permutations = 1
    for (i in (n - r + 1)..n) {
        permutations *= i
    }
    return permutations
}

fun combinationsOf(n: Int, r: Int): Int {
    return permutationsOf(n, r) / factorialOf(r)
}

fun main() {
    BinomialExpansionMiniApp(::printBinomialExpansion)
}
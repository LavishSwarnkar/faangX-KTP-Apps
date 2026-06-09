package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.BinomialExpansion

private fun binomialCoefficient(n: Int, r: Int): Long {
    var result = 1L
    for (i in 0 until r) {
        result = result * (n - i) / (i + 1)
    }
    return result
}

fun printBinomialExpansion(n: Int) {
    val terms = (0..n).map { k ->
        val coeff = binomialCoefficient(n, k)
        val aPow = n - k
        val bPow = k
        buildString {
            if (coeff != 1L || (aPow == 0 && bPow == 0)) append(coeff)
            if (aPow > 0) {
                append("a")
                if (aPow > 1) append("^$aPow")
            }
            if (bPow > 0) {
                append("b")
                if (bPow > 1) append("^$bPow")
            }
        }
    }
    print(terms.joinToString(" + "))
}

@Composable
fun BinomialExpansionDemo() {
    BinomialExpansion(
        printBinomialExpansion = ::printBinomialExpansion
    )
}

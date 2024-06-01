package com.faangx.ktp.test

import com.faangx.ktp.basics.BinomialExpansionFunctionality
import com.faangx.ktp.test.FactorialCalculatorTest.combinationsOf
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest

@MiniAppTest
object BinomialExpansionTest {

    fun test(
        functionality: BinomialExpansionFunctionality,
        testcase: Int
    ) {
        val expected = captureStdOutput { printBinomialExpansion(testcase) }
        val actual = captureStdOutput { functionality.printBinomialExpansion1(testcase) }
        assert(actual == expected) {
            "Wrong output for $testcase : ($actual). Instead, ($expected) was expected"
        }
    }

    fun testcases(): List<Int> {
        return listOf(1, 2, 3, 4, 8, 11, 20)
    }

    internal fun printBinomialExpansion(n: Int) {
        repeat(n + 1) { i ->
            val coefficient = combinationsOf(n.toLong(), i.toLong())
            if (coefficient > 1) print(coefficient)

            val powerOfA = n - i
            if (powerOfA > 0) print("a")
            if (powerOfA > 1) print("^$powerOfA")

            if (i > 0) print("b")
            if (i > 1) print("^$i")

            if (i != n) print(" + ")
        }
    }

}
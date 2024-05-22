package com.faangx.ktp.test

import com.faangx.ktp.basics.FactorialCalculatorAppFunctionality
import ksp.MiniAppTest
import java.util.*

data class FactorialCalculatorTestcase(
    val n: Long,
    val r: Long,
    val factorial: Long,
    val p: Long,
    val c: Long
)

@MiniAppTest
object FactorialCalculatorTest {

    fun test(
        functionality: FactorialCalculatorAppFunctionality,
        testcase: FactorialCalculatorTestcase
    ) {
        testcase.run {
            val actualFactorial = functionality.factorialOf1(n)

            assert(actualFactorial == factorial) {
                "Wrong factorial ($actualFactorial) for $n, expected $factorial"
            }

            val actualP = functionality.permutationsOf1(n, r)
            assert(actualP == p) {
                "Wrong permutations ($actualP) for (n=$n, r=$r), expected $p"
            }

            val actualC = functionality.combinationsOf1(n, r)
            assert(actualC == c) {
                "Wrong combinations ($actualC) for (n=$n, r=$r), expected $c"
            }
        }
    }

    fun testcases(): List<FactorialCalculatorTestcase> {
        return buildList {
            repeat(15) {
                val n = Random().nextLong(30)
                val r = Random().nextLong(15)

                val factorialOf = { x: Long ->
                    (2L..x).fold(1L) { acc, i -> acc * i }
                }

                val p = ((n - r + 1)..n).fold(1L) { acc, i -> acc * i }
                val c = p / factorialOf(r)

                add(FactorialCalculatorTestcase(n, r, factorialOf(n), p, c))
            }
        }
    }

}
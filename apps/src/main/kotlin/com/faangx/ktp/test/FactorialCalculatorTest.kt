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

                val p = permutationsOf(n, r)
                val c = p / factorialOf(r)

                add(FactorialCalculatorTestcase(n, r, factorialOf(n), p, c))
            }
        }
    }

    internal fun factorialOf(x: Long): Long {
        var factorial = 1L
        for (i in 2L..x) {
            factorial *= i
        }
        return factorial
    }

    internal fun permutationsOf(n: Long, r: Long): Long {
        var permutations = 1L
        for (i in (n - r + 1)..n) {
            permutations *= i
        }
        return permutations
    }

    internal fun combinationsOf(n: Long, r: Long): Long {
        return permutationsOf(n, r) / factorialOf(r)
    }

}
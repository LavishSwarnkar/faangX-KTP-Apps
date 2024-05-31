package com.faangx.ktp.test

import com.faangx.ktp.basics.FactorCalculatorFunctionality
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest
import java.util.*

data class FactorCalculatorTestcase(
    val num: Int,
    val factors: List<Int>
)

@MiniAppTest
object FactorCalculatorTest {

    fun test(
        functionality: FactorCalculatorFunctionality,
        testcase: FactorCalculatorTestcase
    ) {
        val actualFactors = captureStdOutput { functionality.printFactorsOf1(testcase.num) }
            .split(", ")
            .dropLast(1)
            .map { it.toInt() }

        val actualIsPrime = functionality.isPrime1(testcase.num)

        assert(actualFactors == testcase.factors) {
            "Wrong factors $actualFactors for ${testcase.num}, expected ${testcase.factors}"
        }

        assert(actualIsPrime == (testcase.factors.size == 2)) {
            "Wrong prime check ($actualIsPrime) for ${testcase.num}, expected ${!actualIsPrime}"
        }
    }

    fun testcases(): List<FactorCalculatorTestcase> {
        return buildList {
            repeat(15) {
                val num = Random().nextInt(100)
                val factors = (1..num).filter { num % it == 0 }
                add(FactorCalculatorTestcase(num, factors))
            }
        }
    }

    internal fun printFactorsOf(num: Int) {
        for (i in 1..num) {
            if (num % i == 0) {
                print("$i, ")
            }
        }
    }

    internal fun isPrime(num: Int): Boolean {
        var count = 0
        for (i in 1..num) {
            if (num % i == 0) {
                count++
            }
        }
        return count == 2
    }

}
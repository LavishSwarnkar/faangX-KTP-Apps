package com.faangx.ktp.test

import com.faangx.ktp.basics.FactorCalculatorFunctionality
import com.faangx.ktp.basics.FactorCalculatorV1Functionality
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest
import java.util.*

@MiniAppTest
object FactorCalculatorV1Test {

    fun test(
        functionality: FactorCalculatorV1Functionality,
        testcase: FactorCalculatorTestcase
    ) {
        val actualFactors = functionality.getFactorsOf1(testcase.num)
        val actualIsPrime = functionality.isPrime1(testcase.num)

        assert(actualFactors == testcase.factors) {
            "Wrong factors $actualFactors for ${testcase.num}, expected ${testcase.factors}"
        }

        assert(actualIsPrime == (testcase.factors.size == 2)) {
            "Wrong prime check ($actualIsPrime) for ${testcase.num}, expected ${!actualIsPrime}"
        }
    }

    internal fun getFactorsOf(num: Int): List<Int> {
        return buildList {
            for (i in 1..num) {
                if (num % i == 0) add(i)
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
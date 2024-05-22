package com.faangx.ktp.test

import com.faangx.ktp.basics.OddEvenCheckerFunctionality
import com.faangx.ktp.test.mobile.OddEvenCheckerTestCase
import ksp.MiniAppTest

@MiniAppTest
object OddEvenCheckerTest {

    fun test(
        functionality: OddEvenCheckerFunctionality,
        testcase: OddEvenCheckerTestCase
    ) {
        val (num, expected) = testcase
        val result = functionality.checkEvenOdd1(num)
        assert(result == expected) {
            "Incorrect $result for num=$num, expected $expected"
        }
    }

    fun testcases(): List<OddEvenCheckerTestCase> {
        return (1..20).map {
            it to if (it % 2 == 0) "Even" else "Odd"
        }
    }

}
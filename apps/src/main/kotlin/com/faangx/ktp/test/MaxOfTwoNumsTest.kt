package com.faangx.ktp.test

import com.faangx.ktp.basics.MaxOfTwoNumsAppFunctionality
import com.faangx.ktp.test.mobile.MaxOfTwoNumsTestCase
import ksp.MiniAppTest
import kotlin.math.max
import kotlin.random.Random

@MiniAppTest
object MaxOfTwoNumsTest {

    fun test(
        functionality: MaxOfTwoNumsAppFunctionality,
        testcase: MaxOfTwoNumsTestCase
    ) {
        val (a, b, expected) = testcase
        val result = functionality.maxOf1(a, b)
        assert(result == expected) {
            "Incorrect $result for ($a, $b), expected $expected"
        }
    }

    fun testcases(): List<MaxOfTwoNumsTestCase> {
        return buildList {
            repeat(15) {
                val a = Random.nextInt(1000)
                val b = Random.nextInt(1000)
                add(Triple(a, b, max(a, b)))
            }
        }
    }

    internal fun maxOf(x: Int, y: Int): Int {
        return if (x > y) x else y
    }

}
package com.faangx.ktp.test

import com.faangx.ktp.basics.MaxOfThreeNumsAppFunctionality
import com.faangx.ktp.test.mobile.MaxOfThreeNumsTestCase
import ksp.MiniAppTest
import kotlin.math.max
import kotlin.random.Random

@MiniAppTest
object MaxOfThreeNumsTest {

    fun test(
        functionality: MaxOfThreeNumsAppFunctionality,
        testcase: MaxOfThreeNumsTestCase
    ) {
        val (nums, expected) = testcase
        val (a, b, c) = nums
        val result = functionality.maxOf1(a, b, c)
        assert(result == expected) {
            "Incorrect $result for ($a, $b, $c), expected $expected"
        }
    }

    fun testcases(): List<MaxOfThreeNumsTestCase> {
        return buildList {
            repeat(15) {
                val a = Random.nextInt(1000)
                val b = Random.nextInt(1000)
                val c = Random.nextInt(1000)
                add(Triple(a, b, c) to max(max(a, b), c))
            }
        }
    }

    internal fun maxOf(x: Int, y: Int, z: Int): Int {
        return if (x > y && x > z) x else if (y > x && y > z) y else z
    }

}
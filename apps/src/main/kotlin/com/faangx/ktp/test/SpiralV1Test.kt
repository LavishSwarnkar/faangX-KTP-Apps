package com.faangx.ktp.test

import com.faangx.ktp.basics.SpiralV1AppFunctionality
import ksp.MiniAppTest

@MiniAppTest
object SpiralV1Test {

    fun test(
        spiralAppFunctionality: SpiralV1AppFunctionality,
        testcase: Int
    ) {
        val result = spiralAppFunctionality.getFibonacciSeries1(testcase)
        val expected = getFibonacciSeries(testcase)

        assert(result == expected) {
            "Incorrect output \"$result\" for n=$testcase, expected \"$expected\""
        }
    }

    internal fun getFibonacciSeries(n: Int): List<Int> {
        var a = 1
        var b = 1

        return buildList {
            repeat(n) {
                if (it <= 1) {
                    add(1)
                } else {
                    val sum = a + b
                    add(sum)
                    a = b
                    b = sum
                }
            }
        }
    }

}
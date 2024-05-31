package com.faangx.ktp.test

import com.faangx.ktp.basics.loops.SpiralAppFunctionality
import com.faangx.ktp.util.captureStdOutput

object SpiralAppTest {

    fun test(
        spiralAppFunctionality: SpiralAppFunctionality,
        testcase: Int
    ) {
        val result = captureStdOutput {
            spiralAppFunctionality.printFibonacciSeries1(testcase)
        }
        val expected = captureStdOutput {
            printFibonacciSeries(testcase)
        }
        assert(result == expected) {
            "Incorrect output \"$result\" for n=$testcase, expected \"$expected\""
        }
    }

    internal fun printFibonacciSeries(n: Int) {
        var a = 1L
        var b = 1L

        repeat(n) {
            if (it <= 1) {
                print("1, ")
            } else {
                val sum = a + b
                print("$sum, ")
                a = b
                b = sum
            }
        }
    }

    fun testcases() = (1..20).toList()

}
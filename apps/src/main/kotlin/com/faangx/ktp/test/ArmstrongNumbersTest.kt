package com.faangx.ktp.test

import com.faangx.ktp.basics.ArmstrongNumbersFunctionality
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest

@MiniAppTest
object ArmstrongNumbersTest {

    fun test(
        functionality: ArmstrongNumbersFunctionality,
        testcase: Int
    ) {
        val result = captureStdOutput {
            functionality.printArmstrongNums1(testcase)
        }
        val expected = captureStdOutput {
            printArmstrongNums(testcase)
        }

        assert(result == expected) {
            "Wrong output \"$result\" for $testcase, expected \"$expected\""
        }
    }

    internal fun printArmstrongNums(upTo: Int) {
        for (i in 0..upTo) {
            if (isArmstrong(i)) {
                print("$i, ")
            }
        }
    }

    internal fun isArmstrong(num: Int): Boolean {
        val n = noOfDigits(num)

        var sum = 0
        var x = num
        while (x > 0) {
            val digit = x % 10
            x /= 10
            sum += pow(digit, n)
        }
        return sum == num
    }

    private fun noOfDigits(num: Int): Int {
        var x = num
        var digits = 0
        while (x > 0) {
            x /= 10
            digits++
        }
        return digits
    }

    private fun pow(base: Int, exp: Int): Int {
        var res = 1
        repeat(exp) {
            res *= base
        }
        return res
    }

    fun testcases(): List<Int> {
        return buildSet {
            addAll(
                listOf(
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407
                )
            )
            addAll(
                (30..50).toList()
            )
            addAll(
                (300..330).toList()
            )
        }.toList()
    }

}
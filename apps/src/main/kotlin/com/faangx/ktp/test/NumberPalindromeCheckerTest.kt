package com.faangx.ktp.test

import com.faangx.ktp.basics.NumberPalindromeCheckerFunctionality
import ksp.MiniAppTest

@MiniAppTest
object NumberPalindromeCheckerTest {

    fun test(
        functionality: NumberPalindromeCheckerFunctionality,
        testcase: Long
    ) {
        val reverseRes = functionality.reverseNum1(testcase)
        val reverseExp = reverseNum(testcase)

        val isPalindromeRes = functionality.isPalindrome1(testcase)
        val isPalindromeExp = isPalindrome(testcase)

        assert(reverseRes == reverseExp) {
            "Wrong reverse $reverseRes for num = $testcase, expected $reverseExp"
        }

        assert(isPalindromeRes == isPalindromeExp) {
            "Wrong output $isPalindromeRes for num = $testcase, expected $isPalindromeExp"
        }
    }

    internal fun reverseNum(num: Long): Long {
        var reverse = 0L
        var x = num
        while (x > 0) {
            val rem = x % 10
            x /= 10
            reverse = reverse * 10 + rem
        }
        return reverse
    }

    internal fun isPalindrome(num: Long): Boolean {
        return num == reverseNum(num)
    }

    fun testcases(): List<Long> {
        return listOf(
            1, 2, 5, 11, 22, 43, 34, 76, 121, 12321, 1988, 554455
        )
    }

}
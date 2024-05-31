package com.faangx.ktp.test

import com.faangx.ktp.basics.StringPalindromeCheckerFunctionality

object StringPalindromeCheckerTest {

    fun test(
        functionality: StringPalindromeCheckerFunctionality,
        testcase: String
    ) {
        val reverseRes = functionality.reverseStr1(testcase)
        val reverseExp = reverseStr(testcase)

        val isPalindromeRes = functionality.isPalindrome1(testcase, false)
        val isPalindromeExp = isPalindrome(testcase, false)

        val isPalindromeIgCaseRes = functionality.isPalindrome1(testcase, true)
        val isPalindromeIgCaseExp = isPalindrome(testcase, true)

        assert(reverseRes == reverseExp) {
            "Wrong reverse \"$reverseRes\" for str = $testcase, expected \"$reverseExp\""
        }

        assert(isPalindromeRes == isPalindromeExp) {
            "Wrong output $isPalindromeRes for str = $testcase, expected $isPalindromeExp"
        }

        assert(isPalindromeIgCaseRes == isPalindromeIgCaseExp) {
            "Wrong output $isPalindromeRes for str = $testcase (ignoreCase), expected $isPalindromeExp"
        }
    }

    fun testcases(): List<String> {
        return listOf(
            "", "A", "BA", "ABA", "abA", "abcdCbA", " Abc BA"
        )
    }

    internal fun reverseStr(str: String): String {
        return buildString {
            for (i in str.lastIndex downTo 0) {
                append(str[i])
            }
        }
    }

    internal fun isPalindrome(str: String, ignoreCase: Boolean): Boolean {
        if (str.isBlank()) return false

        return areStringsEqual(str, reverseStr(str), ignoreCase)
    }

    private fun areStringsEqual(s1: String, s2: String, ignoreCase: Boolean): Boolean {
        if (s1.length != s2.length) return false

        for (i in s1.indices) {
            if (!areCharsEqual(s1[i], s2[i], ignoreCase)) return false
        }

        return true
    }

    private fun areCharsEqual(c1: Char, c2: Char, ignoreCase: Boolean): Boolean {
        if (!ignoreCase) return c1 == c2
        return convertToLowercase(c1) == convertToLowercase(c2)
    }

    private fun convertToLowercase(char: Char): Char {
        return if (char in 'A'..'Z') {
            Char(char.code + 32)
        } else {
            char
        }
    }

}
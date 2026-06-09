package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.StringPalindromeChecker

fun reverseStr(str: String): String = str.reversed()

fun isPalindrome(str: String, ignoreCase: Boolean): Boolean =
    str.equals(str.reversed(), ignoreCase)

@Composable
fun StringPalindromeCheckerDemo() {
    StringPalindromeChecker(
        reverseStr = ::reverseStr,
        isPalindrome = ::isPalindrome
    )
}

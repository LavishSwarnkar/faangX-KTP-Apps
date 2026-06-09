package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.NumberPalindromeChecker

fun reverseNum(num: Long): Long = num.toString().reversed().toLong()

fun isPalindrome(num: Long): Boolean = num == reverseNum(num)

@Composable
fun NumberPalindromeCheckerDemo() {
    NumberPalindromeChecker(
        reverseNum = ::reverseNum,
        isPalindrome = ::isPalindrome
    )
}

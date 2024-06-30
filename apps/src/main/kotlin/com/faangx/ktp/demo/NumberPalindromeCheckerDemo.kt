package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.NumberPalindromeChecker
import com.faangx.ktp.basics.NumberPalindromeCheckerMiniApp

fun reverseNum(num: Long): Long {
    var reverse = 0L
    var x = num
    while (x > 0) {
        val rem = x % 10
        x /= 10
        reverse = reverse * 10 + rem
    }
    return reverse
}

fun isPalindrome(num: Long): Boolean {
    return num == reverseNum(num)
}

fun main() {
    NumberPalindromeCheckerMiniApp(
        ::reverseNum, ::isPalindrome
    )
}

@Composable
fun NumberPalindromeCheckerDemo() {
    NumberPalindromeChecker(
        reverseNum = { it.toString().reversed().toLong() },
        isPalindrome = { it.toString().reversed() == it.toString() }
    )
}
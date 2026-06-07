package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.LeapYearChecker

fun isLeapYear(year: Int): Boolean {
    if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
        return true
    } else {
        return false
    }
}

@Composable
fun LeapYearCheckerDemo() {
    LeapYearChecker(::isLeapYear)
}
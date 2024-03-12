package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.LeapYearChecker

@Composable
fun LeapYearCheckerDemo() {
    LeapYearChecker { year ->
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return@LeapYearChecker true
        } else {
            return@LeapYearChecker false
        }
    }
}
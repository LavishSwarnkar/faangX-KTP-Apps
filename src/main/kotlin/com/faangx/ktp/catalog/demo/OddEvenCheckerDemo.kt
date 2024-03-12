package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.OddEvenChecker

@Composable
fun OddEvenCheckerDemo() {
    OddEvenChecker { num ->
        if (num % 2 == 0) "Even" else "Odd"
    }
}
package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.VoteAgeChecker

@Composable
fun VoteAgeCheckAppDemo() {
    VoteAgeChecker {
        if (it >= 18) {
            return@VoteAgeChecker true
        } else {
            return@VoteAgeChecker false
        }
    }
}
package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.VoteAgeChecker

fun canVote(age: Int): Boolean {
    if (age >= 18) {
        return true
    } else {
        return false
    }
}

@Composable
fun VoteAgeCheckAppDemo() {
    VoteAgeChecker(::canVote)
}
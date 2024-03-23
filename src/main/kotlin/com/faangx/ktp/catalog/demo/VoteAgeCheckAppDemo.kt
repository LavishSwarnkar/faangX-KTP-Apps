package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.VoteAgeChecker

fun canVote(age: Int): Boolean {

    return if (age >= 18) {
        true
    } else {
        false
    }
}

@Composable
fun VoteAgeCheckAppDemo() {
    VoteAgeChecker(::canVote)
}
package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.VoteAgeChecker
import com.faangx.ktp.basics.VoteAgeCheckerMiniApp

fun canVote(age: Int): Boolean {
    if (age >= 18) {
        return true
    } else {
        return false
    }
}

fun main() {
    VoteAgeCheckerMiniApp(::canVote)
}

@Composable
fun VoteAgeCheckAppDemo() {
    VoteAgeChecker(::canVote)
}
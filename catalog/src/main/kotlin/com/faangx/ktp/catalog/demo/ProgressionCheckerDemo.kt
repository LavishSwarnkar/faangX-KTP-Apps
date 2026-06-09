package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.ProgressionChecker

fun checkForAP(series: List<Int>): String {
    if (series.size < 2) return "Need at least 2 terms"
    val commonDifference = series[1] - series[0]
    val isAp = series.zipWithNext().all { (a, b) -> b - a == commonDifference }
    return if (isAp) {
        "It is an AP with common difference $commonDifference"
    } else {
        "It is not an AP"
    }
}

fun checkForGP(series: List<Int>): String {
    if (series.size < 2) return "Need at least 2 terms"
    if (series.any { it == 0 }) return "It is not a GP"
    val commonRatio = series[1].toDouble() / series[0]
    val isGp = series.zipWithNext().all { (a, b) -> b.toDouble() / a == commonRatio }
    return if (isGp) {
        "It is a GP with common ratio ${commonRatio.toString().removeSuffix(".0")}"
    } else {
        "It is not a GP"
    }
}

@Composable
fun ProgressionCheckerDemo() {
    ProgressionChecker(
        checkForAP = ::checkForAP,
        checkForGP = ::checkForGP
    )
}

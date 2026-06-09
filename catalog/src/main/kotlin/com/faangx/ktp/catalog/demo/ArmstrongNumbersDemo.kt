package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.ArmstrongNumbers
import kotlin.math.pow

private fun isArmstrong(num: Int): Boolean {
    val digits = num.toString().map { it.digitToInt() }
    val power = digits.size
    return digits.sumOf { it.toDouble().pow(power).toInt() } == num
}

fun printArmstrongNums(upTo: Int) {
    for (num in 0..upTo) {
        if (isArmstrong(num)) print("$num, ")
    }
}

@Composable
fun ArmstrongNumbersDemo() {
    ArmstrongNumbers(
        printArmstrongNums = ::printArmstrongNums
    )
}

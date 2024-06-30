package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.RomanNumeralConvertor
import com.faangx.ktp.basics.RomanNumeralConvertorMiniApp
import com.faangx.ktp.basics.SpreadsheetColNameConvertor
import com.faangx.ktp.basics.SpreadsheetColNameConvertorMiniApp

fun getNumeral(num: Int): String {
    val map = mapOf(
        1000 to "M", 900 to "CM",
        500 to "D", 400 to "CD",
        100 to "C", 90 to "XC",
        50 to "D", 40 to "XD",
        10 to "X", 9 to "IX",
        5 to "V", 4 to "IV",
        1 to "I"
    )

    return buildString {
        var x = num
        while (x > 0) {
            val maxNum = map.keys.first { it <= x }
            val multiplier = x / maxNum
            repeat(multiplier) { append(map[maxNum]) }
            x -= multiplier * maxNum
        }
    }
}

fun getNum(numeral: String): Int {
    val map = mapOf(
        'M' to 1000,
        'D' to 500,
        'C' to 100,
        'L' to 50,
        'X' to 10,
        'V' to 5,
        'I' to 1
    )

    var num = 0
    var lastDigitValue = 0

    for (i in numeral.lastIndex downTo 0) {
        val digitValue = map[numeral[i]]
            ?: error("Invalid char found at $i: ${numeral[i]}")

        if (digitValue >= lastDigitValue) {
            num += digitValue
            lastDigitValue = digitValue
        } else {
            num -= digitValue
        }
    }

    return num
}

fun main() {
    RomanNumeralConvertorMiniApp(
        ::getNumeral,
        ::getNum
    )
}

@Composable
fun RomanNumeralConvertorDemo() {
    RomanNumeralConvertor(
        ::getNumeral,
        ::getNum
    )
}
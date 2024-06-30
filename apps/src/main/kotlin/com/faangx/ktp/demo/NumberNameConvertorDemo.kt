package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.NamingSystem
import com.faangx.ktp.basics.NamingSystem.Indian
import com.faangx.ktp.basics.NamingSystem.International
import com.faangx.ktp.basics.NumberNameConvertor
import com.faangx.ktp.basics.NumberNameConvertorMiniApp

fun getName(
    num: Long,
    system: NamingSystem
): String {
    val tens = listOf(
        "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    )

    val teens = mapOf(
        10 to "Ten",
        11 to "Eleven",
        12 to "Twelve",
        13 to "Thirteen",
        14 to "Fourteen",
        15 to "Fifteen",
        16 to "Sixteen",
        17 to "Seventeen",
        18 to "Eighteen",
        19 to "Nineteen"
    )

    val units = mapOf(
        1 to "One",
        2 to "Two",
        3 to "Three",
        4 to "Four",
        5 to "Five",
        6 to "Six",
        7 to "Seven",
        8 to "Eight",
        9 to "Nine"
    )

    if (num < 100) {
        val tensDigit = (num / 10).toInt()
        val unitDigit = (num % 10).toInt()

        val numInt = num.toInt()

        return when {
            num < 10 -> {
                units[numInt] ?: ""
            }

            num < 20 -> {
                teens[numInt] ?: ""
            }

            else -> {
                listOfNotNull(
                    tens[tensDigit - 1],
                    units[unitDigit]
                ).joinToString(" ")
            }
        }
    }

    for (powerOfTen in system.powersOfTen) {
        if (num >= powerOfTen.key) {
            val quotient: Long = num / powerOfTen.key
            val remainder: Long = num % powerOfTen.key

            var remainderName = getName(remainder, system).ifBlank { null }

            if (powerOfTen.key == 100L && remainderName != null) {
                remainderName = "and $remainderName"
            }

            val powerOfTenName =
                if (powerOfTen.key > 100 && remainderName != null) "${powerOfTen.value}," else powerOfTen.value

            return listOfNotNull(
                getName(quotient, system),
                powerOfTenName,
                remainderName
            ).joinToString(" ")
        }
    }

    error("Impossible case : $num is neither < 100 nor >= 100")
}

fun getFormattedNum(
    num: Long,
    system: NamingSystem
): String {
    val reverse = num.toString().reversed()

    return buildString {
        reverse.forEachIndexed { i, c ->
            val addPeriod = when (system) {
                Indian -> {
                    // 1000000,00,000
                    i == 3 || (i >= 5 && i % 2 == 1)
                }
                International -> {
                    i > 0 && i % 3 == 0
                }
            }
            if (addPeriod) append(',')
            append(c)
        }
    }.reversed()
}

fun main() {
    NumberNameConvertorMiniApp(
        ::getName,
        ::getFormattedNum
    )
}

@Composable
fun NumberNameConvertorDemo() {
    NumberNameConvertor(
        ::getName,
        ::getFormattedNum
    )
}
package com.faangx.ktp.test

import androidx.compose.ui.util.fastJoinToString
import com.faangx.ktp.basics.ProgressionCheckerFunctionality
import ksp.MiniAppTest
import java.util.*

data class ProgressionCheckerTestcase(
    val series: List<Int>,
    val apCheckResult: String,
    val gpCheckResult: String
)

//@MiniAppTest
object ProgressionCheckerTest {

    fun test(
        functionality: ProgressionCheckerFunctionality,
        testcase: ProgressionCheckerTestcase
    ) {
        val actualApCheckResult = functionality.checkForAP1(testcase.series)
        val actualGpCheckResult = functionality.checkForGP1(testcase.series)

        assert(actualApCheckResult == testcase.apCheckResult) {
            "Wrong AP Check result : \"$actualApCheckResult\" for ${testcase.series}, expected \"${testcase.apCheckResult}\""
        }

        assert(actualGpCheckResult == testcase.gpCheckResult) {
            "Wrong GP Check result : \"$actualGpCheckResult\" for ${testcase.series}, expected \"${testcase.gpCheckResult}\""
        }
    }

    fun testcases(): List<ProgressionCheckerTestcase> {
        return emptyList()
    }

    fun apExamples(): List<String> {
        return listOf(
            "1, 2, 3, 4, 5, 6, 7",
            "2, 4, 6, 8, 10, 12, 14",
            "5, 10, 15, 20, 25, 30, 35",
            "3, 6, 9, 12, 15, 18, 21",
            "7, 14, 21, 28, 35, 42, 49",
            "10, 20, 30, 40, 50, 60, 70",
            "4, 8, 12, 16, 20, 24, 28",
            "6, 12, 18, 24, 30, 36, 42",
            "9, 18, 27, 36, 45, 54, 63",
            "8, 16, 24, 32, 40, 48, 56"
        )
    }

    fun gpExamples(): List<String> {
        return listOf(
            "1, 2, 4, 8, 16, 32, 64",
            "3, 9, 27, 81, 243, 729, 2187",
            "2, 6, 18, 54, 162, 486, 1458",
            "5, 25, 125, 625, 3125, 15625, 78125",
            "4, 16, 64, 256, 1024, 4096, 16384",
            "7, 49, 343, 2401, 16807, 117649, 823543",
            "10, 100, 1000, 10000, 100000, 1000000, 10000000",
            "8, 32, 128, 512, 2048, 8192, 32768",
            "6, 36, 216, 1296, 7776, 46656, 279936",
            "9, 81, 729, 6561, 59049, 531441, 4782969"
        )
    }

    fun randomExample(): String {
        return buildList {
            val random = Random()
            repeat(7) { add(random.nextInt(100) + 1) }
        }.fastJoinToString(", ")
    }

    internal fun checkForAP(series: List<Int>): String {
        if (series.size < 2) return "too short, can't check"
        val diff = series[1] - series[0]
        for (i in 2..<series.lastIndex) {
            if (series[i + 1] - series[i] != diff) {
                return "NOT an AP"
            }
        }
        return "AP with d = $diff"
    }

    internal fun checkForGP(series: List<Int>): String {
        if (series.size < 2) return "too short, can't check"
        val ratio = series[1] / series[0]
        for (i in 2..<series.lastIndex) {
            if (series[i + 1] / series[i] != ratio) {
                return "NOT a GP"
            }
        }
        return "GP with d = $ratio"
    }

}
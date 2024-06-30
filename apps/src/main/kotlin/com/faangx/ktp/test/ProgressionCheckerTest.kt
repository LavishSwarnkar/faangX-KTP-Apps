package com.faangx.ktp.test

import com.faangx.ktp.basics.ProgressionCheckerFunctionality
import ksp.MiniAppTest

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

    internal fun checkForAP(series: List<Int>): String {
        if (series.size < 2) return "is too short, can't check"
        val diff = series[1] - series[0]
        for (i in 2..series.lastIndex) {
            if (series[i + 1] - series[i] != diff) {
                return "is NOT an AP"
            }
        }
        return "is an AP with d = $diff"
    }

    internal fun checkForGP(series: List<Int>): String {
        if (series.size < 2) return "is too short, can't check"
        val ratio = series[1] / series[0]
        for (i in 2..series.lastIndex) {
            if (series[i + 1] / series[i] != ratio) {
                return "is NOT an GP"
            }
        }
        return "is an GP with d = $ratio"
    }

}
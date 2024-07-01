package com.faangx.ktp.test

import com.faangx.ktp.basics.ProgressionCheckerV1Functionality
import ksp.MiniAppTest

@MiniAppTest
object ProgressionCheckerV1Test {

    fun test(
        functionality: ProgressionCheckerV1Functionality,
        testcase: ProgressionCheckerTestcase
    ) {
        val actualApCheckResult = functionality.checkForAP1(
            testcase.series.joinToString(",")
        )
        val actualGpCheckResult = functionality.checkForGP1(
            testcase.series.joinToString(",")
        )

        assert(actualApCheckResult == testcase.apCheckResult) {
            "Wrong AP Check result : \"$actualApCheckResult\" for ${testcase.series}, expected \"${testcase.apCheckResult}\""
        }

        assert(actualGpCheckResult == testcase.gpCheckResult) {
            "Wrong GP Check result : \"$actualGpCheckResult\" for ${testcase.series}, expected \"${testcase.gpCheckResult}\""
        }
    }

    internal fun checkForAP(input: String): String {
        val series = parseInts(input, ',')
        if (series.size < 2) return "too short, can't check"
        val diff = series[1] - series[0]
        for (i in 2..<series.lastIndex) {
            if (series[i + 1] - series[i] != diff) {
                return "NOT an AP"
            }
        }
        return "AP with d = $diff"
    }

    internal fun checkForGP(input: String): String {
        val series = parseInts(input, ',')
        if (series.size < 2) return "too short, can't check"
        val ratio = series[1] / series[0]
        for (i in 2..<series.lastIndex) {
            if (series[i + 1] / series[i] != ratio) {
                return "NOT a GP"
            }
        }
        return "GP with d = $ratio"
    }

    private fun parseInts(string: String, delimiter: Char): List<Int> {
        val intsStrList = splitString(string, delimiter)
        return buildList {
            for (intStr in intsStrList) {
                add(intStr.toInt())
            }
        }
    }

    private fun splitString(string: String, char: Char): List<String> {
        return buildList {
            buildString {
                for (c in string) {
                    if (c == char) {
                        if (isNotEmpty()) add(toString())
                        clear()
                    } else {
                        append(c)
                    }
                }
                if (isNotEmpty()) add(toString())
            }
        }
    }

}
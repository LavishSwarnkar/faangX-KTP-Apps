package com.faangx.ktp.patterns.test

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.patterns.functionality.PatternLinesAndCharBasedFunctionality
import com.faangx.ktp.patterns.functionality.PatternLinesAndTwoCharsBasedFunctionality
import com.faangx.ktp.util.captureStdOutput

open class PatternLinesAndTwoCharsBasedTest {

    fun test(
        patternCode: String,
        lines: Int
    ) {
        val functionality = MiniAppFunctionalityHelper.getFunctionality(
            "Pattern-$patternCode"
        ) as PatternLinesAndTwoCharsBasedFunctionality

        test(
            patternCode, lines, functionality::printPattern1
        )
    }

    fun test(
        patternCode: String,
        lines: Int,
        printPattern: (lines: Int, char1: Char, char2: Char) -> Unit
    ) {
        val actual = captureStdOutput {
            printPattern(lines, '*', '-')
        }

        val expected = captureStdOutput {
            PatternCorrectImplHelper.getForLinesAndTwoCharBased(patternCode)(lines, '*', '-')
        }

        assert(actual == expected) {
            "Wrong pattern $actual for lines=$lines, expected $expected"
        }
    }

}
package com.faangx.ktp.patterns.test

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.patterns.functionality.PatternLinesAndCharBasedFunctionality
import com.faangx.ktp.patterns.functionality.PatternLinesBasedFunctionality
import com.faangx.ktp.util.captureStdOutput

open class PatternLinesBasedTest {

    fun test(
        patternCode: String,
        lines: Int
    ) {
        val functionality = MiniAppFunctionalityHelper.getFunctionality(
            "Pattern-$patternCode"
        ) as PatternLinesBasedFunctionality

        test(
            patternCode, lines, functionality::printPattern1
        )
    }

    companion object {
        fun test(
            patternCode: String,
            lines: Int,
            printPattern: (lines: Int) -> Unit
        ) {
            val actual = captureStdOutput {
                printPattern(lines)
            }

            val expected = captureStdOutput {
                PatternCorrectImplHelper.getForLinesBased(patternCode)(lines)
            }

            assert(actual == expected) {
                "Wrong pattern $actual for lines=$lines, expected $expected"
            }
        }
    }

}
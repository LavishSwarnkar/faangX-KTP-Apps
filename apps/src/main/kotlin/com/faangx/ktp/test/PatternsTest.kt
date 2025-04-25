package com.faangx.ktp.test

import com.faangx.ktp.basics.PatternsAppFunctionality
import com.faangx.ktp.patterns.Pattern.Type.*
import com.faangx.ktp.patterns.getPatterns
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper.getForLinesAndCharBased
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper.getForLinesAndTwoCharBased
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper.getForLinesBased
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper.getForWordBased
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest

data class PatternsTestcase(
    val patternCode: String,
    val lines: Int,
    val customization: String
) {
    override fun toString() = "$patternCode($lines, $customization)"
}

@MiniAppTest
object PatternsTest {

    val patterns = getPatterns()

    fun test(
        functionality: PatternsAppFunctionality,
        testcase: PatternsTestcase
    ) {
        val actual = captureStdOutput {
            with(testcase) {
                functionality.printPattern1(patternCode, lines, customization)
            }
        }
        val expected = captureStdOutput {
            printPattern(testcase)
        }

        assert(actual == expected) {
            "Wrong Pattern Check result : \"$actual\" for $testcase, expected \"$expected\""
        }
    }

    fun printPattern(patternCode: String, lines: Int, customization: String) {
        val pattern = patterns.find { it.code == patternCode }
            ?: error("Pattern with code $patternCode not found")
        when (pattern.type) {
            LinesBased -> getForLinesBased(pattern.code)(lines)
            LinesAndCharBased -> getForLinesAndCharBased(pattern.code)(lines, customization.first())
            LinesAnd2CharBased -> getForLinesAndTwoCharBased(pattern.code)(lines, customization.first(), customization[1])
            WordBased -> getForWordBased(pattern.code)(customization)
        }
    }

    fun printPattern(testcase: PatternsTestcase) {
        with(testcase) {
            printPattern(patternCode, lines, customization)
        }
    }

    fun testcases(): List<PatternsTestcase> {
        return patterns.map { pattern ->
            (1..9 step 2).map { lines ->
                PatternsTestcase(
                    pattern.code, lines, pattern.defaultCustomization ?: ""
                )
            }
        }.flatten()
    }

}
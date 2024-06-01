package com.faangx.ktp.patterns.test

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Pattern_P4_Test: PatternLinesAndTwoCharsBasedTest() {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(lines: Int) {
        test(
            patternCode = "P4",
            lines = lines
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<Int> = listOf(1, 3, 5, 7, 9, 15, 20)
    }

}
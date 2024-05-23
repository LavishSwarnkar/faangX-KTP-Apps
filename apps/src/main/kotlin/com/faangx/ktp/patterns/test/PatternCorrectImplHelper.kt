package com.faangx.ktp.patterns.test

internal object PatternCorrectImplHelper {

    fun getForLinesBased(patternCode: String): (lines: Int) -> Unit {
        return when (patternCode) {
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    fun getForLinesAndCharBased(patternCode: String): (lines: Int, char: Char) -> Unit {
        return when (patternCode) {
            "BL" -> ::patternBL
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    fun getForLinesAndTwoCharBased(patternCode: String): (lines: Int, char1: Char, char2: Char) -> Unit {
        return when (patternCode) {
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    fun getForWordBased(patternCode: String): (word: String) -> Unit {
        return when (patternCode) {
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    private fun patternBL(lines: Int, char: Char) {
        repeat(lines) {
            repeat(it + 1) {
                print(char)
            }
            println()
        }
    }

}
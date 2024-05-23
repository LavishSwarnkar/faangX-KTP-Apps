package com.faangx.ktp.patterns.test

internal object PatternCorrectImplHelper {

    fun getForLinesBased(patternCode: String): (lines: Int) -> Unit {
        return when (patternCode) {
            "TLN" -> ::patternTLN
            "TRSR" -> ::patternTRSR
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    fun getForLinesAndCharBased(patternCode: String): (lines: Int, char: Char) -> Unit {
        return when (patternCode) {
            "BL" -> ::patternBL
            "BR" -> ::patternBR
            "PU1" -> ::patternPU1
            else -> error("Invalid patternCode : $patternCode")
        }
    }

    fun getForLinesAndTwoCharBased(patternCode: String): (lines: Int, char1: Char, char2: Char) -> Unit {
        return when (patternCode) {
            "PU2" -> ::patternPU2
            "PD3" -> ::patternPD3
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

    private fun patternBR(lines: Int, char: Char) {
        repeat(lines) {
            repeat(lines - 1 - it) { print(' ') }
            repeat(it + 1) { print(char) }
            println()
        }
    }

    private fun patternTLN(lines: Int) {
        repeat(lines) { i ->
            repeat(lines - i) { print(lines - i) }
            println()
        }
    }

    private fun patternTRSR(lines: Int) {
        repeat(lines) { i ->
            repeat(i) { print(' ') }
            for (j in lines downTo i+1) { print(j) }
            println()
        }
    }

    private fun patternPU1(lines: Int, char: Char) {
        repeat (lines) { i ->
            repeat(lines - 1 - i) { print(" ") }
            repeat(2 * i + 1) { print(char) }
            println()
        }
    }

    private fun patternPU2(
        lines: Int,
        char1: Char = '*',
        char2: Char = '-'
    ) {
        repeat (lines) { i ->

            // Space
            repeat(lines - 1 - i) { print(" ") }

            // *
            repeat(
                if (i == lines - 1) (2 * i + 1) else 1
            ) { print(char1) }

            // -
            repeat(
                if (i == 0 || i == lines - 1) 0 else (2 * i - 1)
            ) { print(char2) }

            // *
            repeat(
                if (i == 0 || i == lines - 1) 0 else 1
            ) { print(char1) }

            println()
        }
    }

    private fun patternPD3(
        lines: Int,
        char1: Char = '*',
        char2: Char = '-'
    ) {
        repeat(lines) { i ->
            repeat(i) { print(' ') }

            repeat(2 * (lines - i) - 1) { j ->
                if (j % 2 == 0) print(char1) else print(char2)
            }

            println()
        }
    }

}
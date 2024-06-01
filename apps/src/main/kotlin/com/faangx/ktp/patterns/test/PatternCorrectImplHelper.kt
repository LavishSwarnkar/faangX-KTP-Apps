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
            "P1" -> ::patternP1
            "P2" -> ::patternP2
            "P3" -> ::patternP3
            "P4" -> ::patternP4
            "P5" -> ::patternP5
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

    private fun patternP1(lines: Int, char1: Char, char2: Char) {
        repeat(lines) { i ->
            repeat(lines) { j ->
                print(
                    if (j == i) char1 else char2
                )
            }
            println()
        }
    }

    private fun patternP2(lines: Int, char1: Char, char2: Char) {
        repeat(lines) { i ->
            repeat(lines) { j ->
                print(
                    if (j == lines - i - 1) char1 else char2
                )
            }
            println()
        }
    }

    private fun patternP3(lines: Int, char1: Char, char2: Char) {
        repeat(lines) { i ->
            repeat(2 * lines - 1) { j ->
                print(
                    if (j == lines - 1 - i || j == lines - 1 + i) char1 else char2
                )
            }
            println()
        }
    }

    private fun patternP4(lines: Int, char1: Char, char2: Char) {
        for (i in 1..lines) {
            // *
            repeat(i) { print(char1) }

            // -
            repeat(lines) { print(char2) }

            // *
            repeat(2 * (lines - i + 1)) { print(char1) }

            // -
            repeat(lines) { print(char2) }

            // *
            repeat(i) { print(char1) }

            println()
        }
    }

    private fun patternP5(lines: Int, char1: Char, char2: Char) {
        // Part 1
        repeat(lines / 2) { i ->

            repeat(lines) { j ->
                when (j) {
                    i, lines - i - 1 -> print(char2)
                    lines / 2 -> print(char1)
                    else -> print(' ')
                }
            }

            println()
        }

        // Part 2
        repeat(lines) {
            print(char1)
        }
        println()

        // Part 3
        repeat(lines / 2) { i ->

            repeat(lines) { j ->
                when (j) {
                    (lines/2 - 1 - i), (lines/2 + i + 1) -> print(char2)
                    lines / 2 -> print(char1)
                    else -> print(' ')
                }
            }

            println()
        }
    }

}
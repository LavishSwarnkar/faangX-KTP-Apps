package com.faangx.ktp.demo

import com.faangx.ktp.patterns.single.PatternLinesAndTwoCharsBasedMiniApp
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper

fun printPattern(lines: Int, char1: Char, char2: Char) {
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

fun main() {
    PatternLinesAndTwoCharsBasedMiniApp("PD3", PatternCorrectImplHelper.getForLinesAndTwoCharBased("PD3"))
}
package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.patterns.PatternsApp
import com.faangx.ktp.util.print
import com.faangx.ktp.util.println
import java.io.ByteArrayOutputStream

@Composable
fun PatternsAppDemo() {
    PatternsApp(
        printPattern = { patternNo, lines, customization, stream ->
            when (patternNo) {
                1 -> stream.pattern1(
                    lines = lines,
                    char = customization.firstOrNull() ?: '*'
                )
                2 -> stream.pattern2(lines)
                3 -> stream.pattern3(lines)
                4 -> stream.pattern4(lines)
                5 -> stream.pattern5(customization)
                6 -> stream.pattern6(customization)
                7 -> stream.pattern7(lines)
                8 -> stream.pattern8(
                    lines = lines,
                    char = customization.getOrNull(0) ?: '*'
                )
                9 -> stream.pattern9(lines)
                10 -> stream.pattern10(
                    lines = lines,
                    char1 = customization.getOrNull(0) ?: '*',
                    char2 = customization.getOrNull(1) ?: '-'
                )

                11 -> stream.pattern11(
                    lines = lines,
                    char1 = customization.getOrNull(0) ?: '*',
                    char2 = customization.getOrNull(1) ?: '-'
                )

                12 -> stream.pattern12(customization)
                13 -> stream.pattern13(
                    lines = lines,
                    char1 = customization.getOrNull(0) ?: '*',
                    char2 = customization.getOrNull(1) ?: '-'
                )
            }
        },
        getDefaultCustomization = { patternNo ->
            when (patternNo) {
                1, 8 -> "*"
                5, 6, 12 -> "APPLE"
                10, 11, 13 -> "*-"
                else -> null
            }
        }
    )
}

fun ByteArrayOutputStream.pattern1(lines: Int, char: Char) {
    repeat(lines) {
        repeat(it + 1) {
            print(char)
        }
        println()
    }
}

fun ByteArrayOutputStream.pattern2(lines: Int) {
    for (lineNo in 1..lines) {
        for (i in 1..lineNo) print(i)
        println()
    }
}

fun ByteArrayOutputStream.pattern3(lines: Int) {
    for (lineNo in 1..lines) {
        for (i in 1..lineNo) print(lineNo)
        println()
    }
}

fun ByteArrayOutputStream.pattern4(lines: Int) {
    for (lineNo in 1..lines) {
        for (i in 1..lineNo) print(5 - lineNo + 1)
        println()
    }
}

fun ByteArrayOutputStream.pattern5(word: String) {
    word.forEachIndexed { index, c ->
        repeat(index + 1) { print(c) }
        println()
    }
}

fun ByteArrayOutputStream.pattern6(word: String) {
    for (lineNo in word.indices) {
        for (i in 0..lineNo) print(word[i])
        println()
    }
}

fun ByteArrayOutputStream.pattern7(lines: Int) {
    var char = 'A'
    for (lineNo in 1..lines) {
        for (i in 1..lineNo) print(char++)
        println()
    }
}

fun ByteArrayOutputStream.pattern8(lines: Int, char: Char) {
    for (i in 1..lines) {
        repeat(lines - i) { print(" ") }
        repeat(2 * i - 1) { print(char) }
        println()
    }
}

fun ByteArrayOutputStream.pattern9(lines: Int) {
    for (i in 1..lines) {
        repeat(lines - i) { print(" ") }
        repeat(2 * i - 1) {
            print(
                (2 * i).toString(16).uppercase()
            )
        }
        println()
    }
}

fun ByteArrayOutputStream.pattern10(
    lines: Int,
    char1: Char = '*',
    char2: Char = '-'
) {
    for (i in 1..lines) {

        // Space
        repeat(lines - i) { print(" ") }

        // *
        repeat(
            if (i == lines) (2 * i - 1) else 1
        ) { print(char1) }

        // -
        repeat(
            if (i == lines) 0 else (2 * i - 3)
        ) { print(char2) }

        // -
        repeat(
            if (i == 1 || i == lines) 0 else 1
        ) { print(char1) }

        println()
    }
}

fun ByteArrayOutputStream.pattern11(
    lines: Int,
    char1: Char = '*',
    char2: Char = '-'
) {
    for (i in 1..lines) {

        // Space
        repeat(lines - i) { print(" ") }

        // Character
        repeat((2 * i - 1)) {
            print(
                if (it % 2 == 0) char1 else char2
            )
        }

        println()
    }
}

fun ByteArrayOutputStream.pattern12(word: String) {
    val lines = word.length
    for (i in word.indices) {
        repeat(lines - i - 1) { print(" ") }
        repeat(2 * i + 1) { print(word[i]) }
        println()
    }
}

fun ByteArrayOutputStream.pattern13(
    lines: Int,
    char1: Char = '*',
    char2: Char = '-'
) {
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
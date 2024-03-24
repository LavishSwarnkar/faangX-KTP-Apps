package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.patterns.PatternsApp
import com.faangx.ktp.util.print
import com.faangx.ktp.util.println
import java.io.ByteArrayOutputStream

@Composable
fun PatternsAppDemo() {
    PatternsApp(
        printPattern = { patternCode, lines, customization, stream ->
            when (patternCode) {
                "BL" -> stream.patternBL(
                    lines = lines,
                    char = customization.firstOrNull() ?: '*'
                )
                "BR" -> stream.patternBR(
                    lines = lines,
                    char = customization.firstOrNull() ?: '*'
                )
                "TLN" -> stream.patternTLN(lines)
                "TRNR" -> stream.patternTRNR(lines)
                "PU1" -> stream.patternPU1(
                    lines = lines,
                    char = customization.getOrNull(0) ?: '*'
                )
                "PU2" -> stream.patternPU2(
                    lines = lines,
                    char1 = customization.getOrNull(0) ?: '*',
                    char2 = customization.getOrNull(1) ?: '-'
                )
                "PD3" -> stream.patternPD3(
                    lines = lines,
                    char1 = customization.getOrNull(0) ?: '*',
                    char2 = customization.getOrNull(1) ?: '-'
                )
            }
        },
        getDefaultCustomization = { patternNo ->
            when (patternNo) {
                "RAS", "RBS", "PU1" -> "*"
                "PU2", "PD3" -> "*-"
                else -> null
            }
        }
    )
}

fun PatternsApp.patternBL(lines: Int, char: Char) {
    repeat(lines) {
        repeat(it + 1) {
            print(char)
        }
        println()
    }
}

fun PatternsApp.patternBR(lines: Int, char: Char) {
    repeat(lines) {
        repeat(lines - 1 - it) { print(' ') }
        repeat(it + 1) { print(char) }
        println()
    }
}

fun PatternsApp.patternTLN(lines: Int) {
    repeat(lines) { i ->
        repeat(lines - i) { print(lines - i) }
        println()
    }
}

fun PatternsApp.patternTRNR(lines: Int) {
    repeat(lines) { i ->
        repeat(i) { print(' ') }
        for (j in lines downTo i+1) { print(j) }
        println()
    }
}

fun PatternsApp.patternPU1(lines: Int, char: Char) {
    repeat (lines) { i ->
        repeat(lines - 1 - i) { print(" ") }
        repeat(2 * i + 1) { print(char) }
        println()
    }
}

fun PatternsApp.patternPU2(
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

fun PatternsApp.patternPD3(
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
        for (i in 1..lineNo) print(lines - lineNo + 1)
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
package com.faangx.ktp.patterns.single

import com.faangx.ktp.MiniApp

fun PatternLinesBasedMiniApp(
    patternCode: String,
    printPattern: (lines: Int) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternLinesBasedApp(patternCode, printPattern)
        }
    )
}

fun PatternLinesAndCharBasedMiniApp(
    patternCode: String,
    printPattern: (lines: Int, char: Char) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternLinesAndCharBasedApp(patternCode, printPattern)
        }
    )
}

fun PatternLinesAndTwoCharsBasedMiniApp(
    patternCode: String,
    printPattern: (lines: Int, char1: Char, char2: Char) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternLinesAndTwoCharsBasedApp(patternCode, printPattern)
        }
    )
}

fun PatternWordBasedMiniApp(
    patternCode: String,
    printPattern: (word: String) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternWordBasedApp(patternCode, printPattern)
        }
    )
}
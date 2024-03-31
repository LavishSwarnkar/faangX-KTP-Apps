package com.faangx.ktp.patterns.single

import androidx.compose.runtime.Composable
import com.faangx.ktp.MiniApp

@JvmName("PatternMiniApp1")
fun PatternMiniApp(
    patternCode: String,
    printPattern: (lines: Int) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternApp(patternCode, printPattern)
        }
    )
}

@JvmName("PatternMiniApp2")
fun PatternMiniApp(
    patternCode: String,
    printPattern: (lines: Int, char: Char) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternApp(patternCode, printPattern)
        }
    )
}

@JvmName("PatternMiniApp3")
fun PatternMiniApp(
    patternCode: String,
    printPattern: (lines: Int, char1: Char, char2: Char) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternApp(patternCode, printPattern)
        }
    )
}

@JvmName("PatternMiniApp4")
fun PatternMiniApp(
    patternCode: String,
    printPattern: (word: String) -> Unit
) {
    MiniApp(
        title = "Pattern - $patternCode",
        composable = {
            PatternApp(patternCode, printPattern)
        }
    )
}
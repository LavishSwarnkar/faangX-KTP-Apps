package com.faangx.ktp.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.CeaserCipher
import com.faangx.ktp.basics.CeaserCipherMiniApp

fun encode(text: String, delta: Int): String {
    return buildString {
        for (char in text) {
            if (isLetter(char)) {
                append(shiftChar(char, delta))
            } else {
                append(char)
            }
        }
    }
}

fun decode(text: String, delta: Int): String {
    return encode(text, -delta)
}

fun encode(text: String, delta: Int, negative: Boolean): String {
    return encode(text, if (negative) -delta else delta)
}

fun decode(text: String, delta: Int, negative: Boolean): String {
    return decode(text, if (negative) -delta else delta)
}

fun shiftChar(char: Char, delta: Int): Char {
    val start = if (char in 'A'..'Z') 'A' else 'a'
    var shifted = (char.code - start.code + delta) % 26
    if (shifted < 0) shifted += 26
    return (start.code + shifted).toChar()
}

fun isLetter(char: Char): Boolean {
    return char in 'a'..'z' || char in 'A'..'Z'
}

fun main() {
    CeaserCipherMiniApp(
        ::encode, ::decode
    )
}

@Composable
fun CeaserCipherDemo() {
    CeaserCipher(
        encode = ::encode,
        decode = ::decode
    )
}
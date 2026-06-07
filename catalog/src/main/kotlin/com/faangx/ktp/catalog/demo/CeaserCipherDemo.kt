package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.CeaserCipher

fun encode(text: String, delta: Int, negative: Boolean): String {
    val effectiveDelta = if (negative) -delta else delta
    return buildString {
        for (char in text) {
            append(
                if (isLetter(char)) {
                    Char(char.code + effectiveDelta)
                } else {
                    char
                }
            )
        }
    }
}

fun encode2(text: String, delta: Int): String {
    var result = ""
    for (char in text) {
        result += if (isLetter(char)) {
            Char(char.code + delta)
        } else {
            char
        }
    }
    return result
}

fun isLetter(char: Char): Boolean {
    return char in 'a'..'z' || char in 'A'..'Z'
}

fun encode1(text: String, delta: Int): String {
    return text.map { char ->
        if (!char.isLetter()) return@map char
        var incrementedChar = char + delta
        if (incrementedChar !in 'A'..'Z') incrementedChar -= 26
        incrementedChar
    }.joinToString("")
}

fun decode(text: String, delta: Int, negative: Boolean): String {
    val effectiveDelta = if (negative) -delta else delta
    return buildString {
        for (char in text) {
            append(
                if (isLetter(char)) {
                    Char(char.code - effectiveDelta)
                } else {
                    char
                }
            )
        }
    }
}

@Composable
fun CeaserCipherDemo() {
    CeaserCipher(
        encode = ::encode,
        decode = ::decode
    )
}
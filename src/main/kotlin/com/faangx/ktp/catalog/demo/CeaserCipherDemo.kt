package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.string.CeaserCipher

fun encode(text: String, delta: Int): String {
    return text.map { char ->
        if (!char.isLetter()) return@map char
        var incrementedChar = char + delta
        if (incrementedChar !in 'A'..'Z') incrementedChar -= 26
        incrementedChar
    }.joinToString("")
}

fun decode(text: String, delta: Int): String {
    return text.map { char ->
        if (!char.isLetter()) return@map char
        var decrementedChar = char - delta
        if (decrementedChar !in 'A'..'Z') decrementedChar += 26
        decrementedChar
    }.joinToString("")
}

@Composable
fun CeaserCipherDemo() {
    CeaserCipher(
        encode = ::encode,
        decode = ::decode
    )
}
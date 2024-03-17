package com.faangx.ktp.catalog.demo

import androidx.compose.runtime.Composable
import com.faangx.ktp.basics.string.Base64App

val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"

fun encodeV1(text: String): String {
    return buildString {

        for (i in text.indices step 3) {
            append(base64Chars[text[i].code and 0xFC shr 2])
            var byte = text[i].code and 0x03 shl 4
            if (i + 1 < text.length) {
                append(base64Chars[byte or (text[i + 1].code and 0xF0 shr 4)])
                byte = (text[i + 1].code and 0x0F) shl 2
                if (i + 2 < text.length) {
                    append(base64Chars[byte or (text[i + 2].code and 0xC0 shr 6)])
                    append(base64Chars[text[i + 2].code and 0x3F])
                    continue
                }
            }
            append(base64Chars[byte])
        }
    }
}

fun encode(text: String): String {
    return buildString {
        val bytes = text.toByteArray()

        for (i in bytes.indices step 3) {
            val nextThreeBytes = listOfNotNull(
                bytes.getOrNull(i)?.toInt(),
                bytes.getOrNull(i + 1)?.toInt(),
                bytes.getOrNull(i + 2)?.toInt()
            )

            val binaryString = nextThreeBytes.joinToString("") { it.toPaddedString(8) }

            for (j in binaryString.indices step 6) {
                val nextSixBits = binaryString.substring(j, (j + 6).coerceAtMost(binaryString.length))
                    .padEnd(6, '0')
                append(base64Chars[nextSixBits.toInt(2)])
            }
        }
    }
}

fun decodeV1(text: String): String {
    val bytes = text.map { base64Chars.indexOf(it) }
    return buildString {
        for (i in bytes.indices step 4) {
            // (6 + 2) text[i][3..8] 0x3F << 2 | text[i + 1][3..4] 0x30 >> 4
            // (4 + 4) text[i + 1][5..8] 0x0F << 4 | text[i + 2][3..6] 0x3F >> 2
            // (2 + 6) text[i + 2][7..8] 0x03 << 6 | text[i + 3][3..8] 0x3F

            var byte = bytes[i] and 0x3F shl 2
            if (i + 1 < bytes.size) {
                byte = byte or ((bytes[i + 1] and 0x30) shr 4)
                append(Char(byte))

                byte = bytes[i + 1] and 0x0F shl 4
                if (i + 2 < bytes.size) {
                    byte = byte or ((bytes[i + 2] and 0x3C) shr 2)
                    append(Char(byte))

                    byte = (bytes[i + 2] and 0x03) shl 6
                    if (i + 3 < bytes.size) {
                        byte = byte or (bytes[i + 3] and 0x3F)
                        append(Char(byte))
                    }
                }
            }
        }
    }
}

fun decode(text: String): String {
    var copy = text
    var result = ""

    while (copy.isNotEmpty()) {
        val nextFourChars = copy.take(4)
        copy = copy.drop(4)

        val binaryString = nextFourChars.filter { it != '=' }
            .toCharArray()
            .joinToString("") { base64Chars.indexOf(it).toPaddedString(6) }

        for (i in binaryString.indices step 8) {
            val nextEightBits = binaryString.substring(i, (i + 8).coerceAtMost(binaryString.length))
            if (nextEightBits.length == 8) {
                result += nextEightBits.toInt(2).toChar()
            }
        }
    }

    return result
}

fun Int.toPaddedString(length: Int): String =
    toString(2).padStart(length, '0')

@Composable
fun Base64AppDemo() {
    Base64App(
        encode = ::encodeV1,
        decode = ::decodeV1
    )
}
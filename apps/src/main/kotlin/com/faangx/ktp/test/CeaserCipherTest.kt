package com.faangx.ktp.test

import com.faangx.ktp.basics.string.CeaserCipherFunctionality

class CeaserCipherTestcase(
    val text: String,
    val delta: Int,
    val negative: Boolean
) {
    override fun toString(): String {
        return "(\"$text\", $delta, $negative)"
    }
}

object CeaserCipherTest {

    fun test(
        functionality: CeaserCipherFunctionality,
        testcase: CeaserCipherTestcase
    ) {
        val result = testcase.run { functionality.encode1(text, delta, negative) }
        val expected = testcase.run { encode(text, delta, negative) }

        val decodeRes = testcase.run { functionality.decode1(expected, delta, negative) }

        require(result == expected) {
            "Wrong output \"$result\" for input $testcase, expected \"$expected\""
        }

        require(result == expected) {
            "Wrong decode output \"$decodeRes\" for input $expected, expected \"${testcase.text}\""
        }
    }

    fun testcases(): List<CeaserCipherTestcase> {
        val phrases = listOf("Hello, how are you?", "Good morning!", "Thank you very much.", "Please, have a seat.", "Excuse me, can you help me?", "I'm sorry for the inconvenience.", "Have a great day!", "Nice to meet you.", "See you soon.", "Take care.")
        val deltas = listOf(1, 5, 15, 25)
        return buildList {
            phrases.forEach { phrase ->
                deltas.forEach { delta ->
                    add(CeaserCipherTestcase(phrase, delta, true))
                    add(CeaserCipherTestcase(phrase, delta, false))
                }
            }
        }
    }

    private fun encode(text: String, delta: Int): String {
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

    private fun decode(text: String, delta: Int): String {
        return encode(text, -delta)
    }

    internal fun encode(text: String, delta: Int, negative: Boolean): String {
        return encode(text, if (negative) -delta else delta)
    }

    internal fun decode(text: String, delta: Int, negative: Boolean): String {
        return decode(text, if (negative) -delta else delta)
    }

    private fun shiftChar(char: Char, delta: Int): Char {
        val start = if (char in 'A'..'Z') 'A' else 'a'
        var shifted = (char.code - start.code + delta) % 26
        if (shifted < 0) shifted += 26
        return (start.code + shifted).toChar()
    }

    private fun isLetter(char: Char): Boolean {
        return char in 'a'..'z' || char in 'A'..'Z'
    }

}
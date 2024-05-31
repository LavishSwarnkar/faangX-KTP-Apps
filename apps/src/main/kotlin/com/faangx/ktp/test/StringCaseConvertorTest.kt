package com.faangx.ktp.test

import com.faangx.ktp.basics.StringCaseConvertorFunctionality
import com.faangx.ktp.basics.StringCaseConvertorResult

object StringCaseConvertorTest {

    fun test(
        functionality: StringCaseConvertorFunctionality,
        testcase: String
    ) {
        val result = functionality.run {
            StringCaseConvertorResult(
                sentenceCase = convertToSentenceCase1(testcase),
                titleCase = convertToTitleCase1(testcase),
                uppercase = convertToUppercase1(testcase),
                lowercase = convertToLowercase1(testcase),
                snakeCase = convertToSnakeCase1(testcase)
            )
        }.summary()

        val expected = StringCaseConvertorResult(
            sentenceCase = convertToSentenceCase(testcase),
            titleCase = convertToTitleCase(testcase),
            uppercase = convertToUppercase(testcase),
            lowercase = convertToLowercase(testcase),
            snakeCase = convertToSnakeCase(testcase)
        ).summary()

        val common = result intersect expected
        val resultSummary = result subtract common
        val expectedSummary = expected subtract common

        require(result == expected) {
            "Wrong output $resultSummary, expected $expectedSummary, for text = \"$testcase\""
        }
    }

    fun testcases(): List<String> {
        return listOf(
            "wow! did you see  that? the sunset was absolutely breathtaking; the sky turned vibrant hues of orange and pink.    nature's beauty never ceases to amaze me.   ",
            "can you believe it? i've lost my keys again. it's so frustrating!    maybe i should get one of those key finders. what do you think?  ",
            "this is incredible news: our project got approved! let's celebrate.    we've worked so hard for this moment.    are you free this weekend for a small party?   ",
            "hello there! it's been ages since we last met.    how have you been? let's catch up soon.    coffee on me! what's your favorite cafe nowadays?  ",
            "wow, the new phone has some amazing features - 108mp camera, 5g support, and a 5000mah battery.    can you imagine? technology is evolving so rapidly!   ",
            "guess what? i won the lottery! i can't believe my luck.    time to plan a vacation. any suggestions on where to go?    i'm so excited!  ",
            "the concert was phenomenal; the energy was electric!    have you ever seen anything like it? what a performance by the band. truly unforgettable.  ",
            "oh no, the cat knocked over the vase again!    this is the third time this month. do you have any tips for keeping pets out of trouble?   ",
            "breaking news: a major storm is approaching.    stay indoors and stock up on essentials.    safety first! have you prepared your emergency kit?   ",
            "congratulations! you did an excellent job on your presentation.    everyone was impressed. keep up the great work.    let's celebrate your success!   "
        )

    }

    internal fun convertToSentenceCase(text: String): String {
        var capitalize = true
        var requireSpace = false
        return buildString {
            text.forEach { c ->
                if (requireSpace) {
                    if (c != ' ') append(' ')
                    requireSpace = false
                }
                append(
                    if (capitalize) c.uppercaseChar() else c
                )
                if (c in listOf('.', '!', '?')) {
                    capitalize = true; requireSpace = true
                } else {
                    capitalize = (capitalize && c == ' ')
                }
            }
        }
    }

    internal fun convertToTitleCase(text: String): String {
        var capitalize = true
        return buildString {
            for (c in text) {
                if (c != ' ') {
                    append(
                        if (capitalize) convertToUppercase(c) else c
                    )
                }
                capitalize = c in listOf('.', '!', '?', ':', ';', '-', ' ', ',')
            }
        }
    }

    internal fun convertToUppercase(text: String): String {
        return buildString {
            for (c in text) {
                append(convertToUppercase(c))
            }
        }
    }

    internal fun convertToLowercase(text: String): String {
        return buildString {
            for (c in text) {
                append(convertToLowercase(c))
            }
        }
    }

    private fun convertToLowercase(char: Char): Char {
        return if (char in 'A'..'Z') {
            Char(char.code + 32)
        } else {
            char
        }
    }

    internal fun convertToSnakeCase(text: String): String {
        return buildString {
            for (c in text) {
                append(
                    if (c == ' ') '_' else c
                )
            }
        }
    }

    internal fun convertToUppercase(char: Char): Char {
        return if (char in 'a'..'z') {
            Char(char.code - 32)
        } else {
            char
        }
    }

}
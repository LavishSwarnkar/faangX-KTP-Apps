package com.faangx.ktp.test

import com.faangx.ktp.basics.StringStatsCalculatorFunctionality
import com.faangx.ktp.basics.StringStatsCalculatorResult

object StringStatsCalculatorTest {

    fun test(
        functionality: StringStatsCalculatorFunctionality,
        testcase: String
    ) {
        val result = functionality.run {
            StringStatsCalculatorResult(
                noOfChars = calcNoOfChars1(testcase),
                noOfAlphabets = calcNoOfAlphabets1(testcase),
                noOfVowels = calcNoOfVowels1(testcase),
                noOfConsonants = calcNoOfConsonants1(testcase),
                noOfDigits = calcNoOfDigits1(testcase),
                noOfSpecialChars = calcNoOfSpecialChars1(testcase),
                noOfSpaces = calcNoOfSpaces1(testcase),
                noOfWords = calcNoOfWords1(testcase),
                noOfSentences = calcNoOfSentences1(testcase)
            )
        }

        val expected = StringStatsCalculatorResult(
            noOfChars = calcNoOfChars(testcase),
            noOfAlphabets = calcNoOfAlphabets(testcase),
            noOfVowels = calcNoOfVowels(testcase),
            noOfConsonants = calcNoOfConsonants(testcase),
            noOfDigits = calcNoOfDigits(testcase),
            noOfSpecialChars = calcNoOfSpecialChars(testcase),
            noOfSpaces = calcNoOfSpaces(testcase),
            noOfWords = calcNoOfWords(testcase),
            noOfSentences = calcNoOfSentences(testcase)
        )

        val resultStatsList = splitStats(result.toString())
        val expectedStatsList = splitStats(expected.toString())
        val common = resultStatsList intersect expectedStatsList
        val resultStats = (resultStatsList subtract common).toString()
        val expectedStats = (expectedStatsList subtract common).toString()

        assert(result == expected) {
            "Wrong output $resultStats, expected $expectedStats, for text = \"$testcase\""
        }
    }

    private fun splitStats(res: String): Set<String> {
        return res.trim('(', ')').split(", ").toSet()
    }

    fun testcases(): List<String> {
        return listOf(
            "Wow! Did you see that? The sunset was absolutely breathtaking; the sky turned vibrant hues of orange and pink. Nature's beauty never ceases to amaze me.",
            "Can you believe it? I've lost my keys again. It's so frustrating! Maybe I should get one of those key finders. What do you think?",
            "This is incredible news: our project got approved! Let's celebrate. We've worked so hard for this moment. Are you free this weekend for a small party?",
            "Hello there! It's been ages since we last met. How have you been? Let's catch up soon. Coffee on me! What's your favorite cafe nowadays?",
            "Wow, the new phone has some amazing features - 108MP camera, 5G support, and a 5000mAh battery. Can you imagine? Technology is evolving so rapidly!",
            "Guess what? I won the lottery! I can't believe my luck. Time to plan a vacation. Any suggestions on where to go? I'm so excited!",
            "The concert was phenomenal; the energy was electric! Have you ever seen anything like it? What a performance by the band. Truly unforgettable.",
            "Oh no, the cat knocked over the vase again! This is the third time this month. Do you have any tips for keeping pets out of trouble?",
            "Breaking news: A major storm is approaching. Stay indoors and stock up on essentials. Safety first! Have you prepared your emergency kit?",
            "Congratulations! You did an excellent job on your presentation. Everyone was impressed. Keep up the great work. Let's celebrate your success!"
        )
    }

    internal fun calcNoOfChars(text: String): Int {
        return text.length
    }

    internal fun calcNoOfAlphabets(text: String): Int {
        var count = 0
        for (c in text) {
            if (c in 'a'..'z' || c in 'A'..'Z') count++
        }
        return count
    }

    internal fun calcNoOfVowels(text: String): Int {
        var count = 0
        for (c in text) {
            if (c in listOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')) count++
        }
        return count
    }

    internal fun calcNoOfConsonants(text: String): Int {
        return calcNoOfAlphabets(text) - calcNoOfVowels(text)
    }

    internal fun calcNoOfDigits(text: String): Int {
        var count = 0
        for (c in text) {
            if (c in '0'..'9') count++
        }
        return count
    }

    internal fun calcNoOfSpecialChars(text: String): Int {
        return text.length - (calcNoOfAlphabets(text) +
                calcNoOfDigits(text) +
                calcNoOfSpaces(text))
    }

    internal fun calcNoOfSpaces(text: String): Int {
        var count = 0
        for (c in text) {
            if (c in listOf(' ', '\n', '\t')) count++
        }
        return count
    }

    internal fun calcNoOfWords(text: String): Int {
        var count = 0
        var nonPunctuationCharFound = false

        for (c in text) {
            if (c !in listOf(' ', '\t', '\n', '.', ',', '?', '!')) {
                nonPunctuationCharFound = true
            } else if (nonPunctuationCharFound) {
                nonPunctuationCharFound = false
                count++
            }
        }

        if (nonPunctuationCharFound) count++

        return count
    }

    internal fun calcNoOfSentences(text: String): Int {
        var count = 0
        var nonPunctuationCharFound = false

        for (c in text) {
            if (c !in listOf(' ', '\t', '\n', '.', ',', '?', '!')) {
                nonPunctuationCharFound = true
            } else if (c in listOf('.', '!', '?') && nonPunctuationCharFound) {
                nonPunctuationCharFound = false
                count++
            }
        }

        if (nonPunctuationCharFound) count++

        return count
    }

}
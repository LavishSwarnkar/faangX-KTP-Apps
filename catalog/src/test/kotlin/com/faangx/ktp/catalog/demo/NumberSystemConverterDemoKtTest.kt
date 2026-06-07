package com.faangx.ktp.catalog.demo

import com.faangx.ktp.basics.NumberSystem.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class NumberSystemConverterDemoKtTest {

    @ParameterizedTest
    @MethodSource("convertNumberTestCases")
    fun convertNumberTest(testcase: List<String>) {
        val (decimal, binary, hexadecimal, octal) = testcase

        // Performing all 12 conversions and checking the correctness
        assertEquals(decimal, convertNumber(binary, Binary, Decimal))
        assertEquals(decimal, convertNumber(octal, Octal, Decimal))
        assertEquals(decimal, convertNumber(hexadecimal, Hexadecimal, Decimal))
        assertEquals(binary, convertNumber(decimal, Decimal, Binary))
        assertEquals(binary, convertNumber(octal, Octal, Binary))
        assertEquals(binary, convertNumber(hexadecimal, Hexadecimal, Binary))
        assertEquals(octal, convertNumber(decimal, Decimal, Octal))
        assertEquals(octal, convertNumber(binary, Binary, Octal))
        assertEquals(octal, convertNumber(hexadecimal, Hexadecimal, Octal))
        assertEquals(hexadecimal, convertNumber(decimal, Decimal, Hexadecimal))
        assertEquals(hexadecimal, convertNumber(binary, Binary, Hexadecimal))
        assertEquals(hexadecimal, convertNumber(octal, Octal, Hexadecimal))
    }

    companion object {
        @JvmStatic
        fun convertNumberTestCases(): List<List<String>> {
            // listOf(Decimal, Binary, Hexadecimal, Octal)
            return listOf(
                listOf("1", "1", "1", "1"),
                listOf("2", "10", "2", "2"),
                listOf("3", "11", "3", "3"),
                listOf("4", "100", "4", "4"),
                listOf("5", "101", "5", "5"),
                listOf("6", "110", "6", "6"),
                listOf("7", "111", "7", "7"),
                listOf("8", "1000", "8", "10"),
                listOf("9", "1001", "9", "11"),
                listOf("10", "1010", "A", "12"),
                listOf("11", "1011", "B", "13"),
                listOf("12", "1100", "C", "14"),
                listOf("13", "1101", "D", "15"),
                listOf("14", "1110", "E", "16"),
                listOf("15", "1111", "F", "17"),
                listOf("16", "10000", "10", "20"),
                listOf("17", "10001", "11", "21"),
                listOf("18", "10010", "12", "22"),
                listOf("19", "10011", "13", "23"),
                listOf("20", "10100", "14", "24")
            )
        }
    }
}
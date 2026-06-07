package com.faangx.ktp.catalog.demo

import com.faangx.ktp.basics.Denomination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AtmSimulatorDemoKtTest {

    @ParameterizedTest
    @MethodSource("atmTestCases")
    fun getAtmOutputTest(inputAndResult: Pair<Int, Map<Int, Int>>) {
        val input = inputAndResult.first; val result = inputAndResult.second

        val expected = result.map { (value, qty) -> "($value * $qty)" }
            .joinToString(" + ")

        val actual = getAtmOutput(input, Denomination.INRDenominations)
            .map {
                val value = it.key.value
                val qty = it.value
                "($value * $qty)"
            }
            .joinToString(" + ")

        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun atmTestCases(): Stream<Pair<Int, Map<Int, Int>>> {
            return Stream.of(
                1 to mapOf(1 to 1),
                3 to mapOf(2 to 1, 1 to 1),
                6 to mapOf(5 to 1, 1 to 1),
                9 to mapOf(5 to 1, 2 to 2),
                13 to mapOf(10 to 1, 2 to 1, 1 to 1),
                36 to mapOf(20 to 1, 10 to 1, 5 to 1, 1 to 1),
                99 to mapOf(50 to 1, 20 to 2, 5 to 1, 2 to 2),
                147 to mapOf(100 to 1, 20 to 2, 5 to 1, 2 to 1),
                256 to mapOf(200 to 1, 50 to 1, 5 to 1, 1 to 1),
                666 to mapOf(500 to 1, 100 to 1, 50 to 1, 10 to 1, 5 to 1, 1 to 1),
                1999 to mapOf(500 to 3, 200 to 2, 50 to 1, 20 to 2, 5 to 1, 2 to 2),
                2222 to mapOf(2000 to 1, 200 to 1, 20 to 1, 2 to 1)
            )
        }
    }
}
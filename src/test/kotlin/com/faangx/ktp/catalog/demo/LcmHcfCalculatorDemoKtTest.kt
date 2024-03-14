package com.faangx.ktp.catalog.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class LcmHcfCalculatorDemoKtTest {

    data class Testcase(
        val a: Int, val b: Int,
        val lcm: Int, val hcf: Int
    )

    @ParameterizedTest
    @MethodSource("LcmHcfTestCases")
    fun LcmHcfTest(testcase: Testcase) {
        // Assuming lcmOf and hcfOf are methods that calculate the least common multiple and highest common factor
        Assertions.assertEquals(testcase.lcm, lcmOf(testcase.a, testcase.b))
        Assertions.assertEquals(testcase.hcf, hcfOf(testcase.a, testcase.b))
    }

    companion object {
        @JvmStatic
        fun LcmHcfTestCases(): List<Testcase> {
            return listOf(
                Testcase(1, 2, 2, 1),
                Testcase(10, 2, 10, 2),
                Testcase(15, 5, 15, 5),
                Testcase(9, 3, 9, 3),
                Testcase(4, 2, 4, 2),
                Testcase(100, 20, 100, 20),
                Testcase(200, 2, 200, 2),
                Testcase(300, 1, 300, 1),
                Testcase(500, 5, 500, 5),
                Testcase(1000, 500, 1000, 500),
                Testcase(1500, 3, 1500, 3),
                Testcase(2000, 2, 2000, 2),
                Testcase(2500, 5, 2500, 5),
                Testcase(3000, 100, 3000, 100),
                Testcase(3500, 10, 3500, 10),
                Testcase(4000, 5, 4000, 5),
                Testcase(4500, 3, 4500, 3),
                Testcase(5000, 2, 5000, 2),
                Testcase(250, 5, 250, 5),
                Testcase(125, 5, 125, 5),
                Testcase(13, 1, 13, 1)
            )
        }
    }

}
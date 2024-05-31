package com.faangx.ktp.test

import com.faangx.ktp.basics.GreetingAppFunctionality
import ksp.MiniAppTest

typealias GreetingAppTestcase = Pair<String, String>

@MiniAppTest
object GreetingTest {

    fun test(
        functionality: GreetingAppFunctionality,
        testcase: GreetingAppTestcase
    ) {
        val (input, expected) = testcase

        val actual = functionality.greet1(input)

        assert(actual == expected) {
            "Wrong output ($actual) for ($input), expected $expected"
        }
    }

    fun testcases(): List<GreetingAppTestcase> {
        return listOf(
            "A" to "Namaste A",
            "B" to "Namaste B",
            "C" to "Namaste C",
        )
    }

    internal fun greet(name: String): String {
        return "Namaste $name"
    }

}
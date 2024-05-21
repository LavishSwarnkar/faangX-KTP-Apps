package com.faangx.ktp.test

import com.faangx.ktp.basics.SquareOfNumAppV1Functionality
import java.util.*

typealias SquareOfNumTestcase = Pair<Long, Long>

object SquareOfNumTest {

    fun test(
        testcase: SquareOfNumTestcase,
        functionality: SquareOfNumAppV1Functionality
    ) {
        val input = testcase.first
        val expected = testcase.second
        val actual = functionality.getSquareOf1(input)
        assert(actual == expected) {
            "Wrong square ($actual) of $input, expected $expected"
        }
    }

    fun testcases(): List<SquareOfNumTestcase> {
        return buildList {
            repeat(15) {
                val x = Random().nextLong(9999L)
                add(x to x * x)
            }
        }
    }

}
package com.faangx.ktp.test

import com.faangx.ktp.basics.SquareOfNumAppV1Functionality
import ksp.MiniAppTest
import kotlin.random.Random

typealias SquareOfNumTestcase = Pair<Long, Long>

@MiniAppTest
object SquareOfNumV1Test {

    fun test(
        functionality: SquareOfNumAppV1Functionality,
        testcase: SquareOfNumTestcase
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
                val x = Random.nextLong(9999L)
                add(x to x * x)
            }
        }
    }

    internal fun getSquareOf(x: Long) = x * x

}
package com.faangx.ktp.test

import com.faangx.ktp.basics.LeapYearCheckerFunctionality
import com.faangx.ktp.test.mobile.LeapYearCheckerTestCase
import ksp.MiniAppTest

@MiniAppTest
object LeapYearCheckerTest {

    fun test(
        functionality: LeapYearCheckerFunctionality,
        testcase: LeapYearCheckerTestCase
    ) {
        val (year, expected) = testcase
        val result = functionality.isLeapYear1(year)
        assert(result == expected) {
            "Incorrect $result for year=$year, expected $expected"
        }
    }

    fun testcases(): List<LeapYearCheckerTestCase> {
        return listOf(
            1000 to false,
            1004 to true,
            1100 to false,
            1200 to true,
            1300 to false,
            1400 to false,
            1600 to true,
            2000 to true,
            2100 to false,
            2024 to true,
        )
    }

    internal fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

}
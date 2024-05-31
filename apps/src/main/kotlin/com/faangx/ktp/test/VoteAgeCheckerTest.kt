package com.faangx.ktp.test

import com.faangx.ktp.basics.VoteAgeCheckerFunctionality
import com.faangx.ktp.test.mobile.VoteAgeCheckerTestCase
import ksp.MiniAppTest

@MiniAppTest
object VoteAgeCheckerTest {

    fun test(
        functionality: VoteAgeCheckerFunctionality,
        testcase: VoteAgeCheckerTestCase
    ) {
        val age = testcase.first
        val expected = testcase.second
        val result = functionality.canVote1(age)
        assert(result == expected) {
            "Incorrect $result returned for age=$age, expected $expected"
        }
    }

    fun testcases(): List<VoteAgeCheckerTestCase> {
        return listOf(
            1 to false,
            10 to false,
            17 to false,
            18 to true,
            25 to true,
            100 to true,
            200 to true,
        )
    }

    internal fun canVote(age: Int): Boolean {
        return age >= 18
    }

}
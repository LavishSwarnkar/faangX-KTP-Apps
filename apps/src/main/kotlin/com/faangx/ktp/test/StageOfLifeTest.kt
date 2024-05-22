package com.faangx.ktp.test

import com.faangx.ktp.basics.StageOfLifeAppFunctionality
import com.faangx.ktp.test.mobile.StageOfLifeTestCase
import ksp.MiniAppTest

@MiniAppTest
object StageOfLifeTest {

    fun test(
        functionality: StageOfLifeAppFunctionality,
        testcase: StageOfLifeTestCase
    ) {
        val (age, expected) = testcase
        val result = functionality.stageOfLife1(age)
        assert(result == expected) {
            "Incorrect $result for age = $age, expected $expected"
        }
    }

    fun testcases(): List<StageOfLifeTestCase> {
        return listOf(
            1 to "Infancy",
            2 to "Infancy",
            3 to "Childhood",
            9 to "Childhood",
            12 to "Teenage",
            16 to "Teenage",
            18 to "Adulthood",
            50 to "Adulthood",
            60 to "Old age",
            100 to "Old age",
            200 to "Old age",
        )
    }

}
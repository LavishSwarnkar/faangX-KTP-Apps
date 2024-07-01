package com.faangx.ktp.test

import com.faangx.ktp.basics.ArmstrongNumbersV1Functionality
import ksp.MiniAppTest

@MiniAppTest
object ArmstrongNumbersV1Test {

    fun test(
        functionality: ArmstrongNumbersV1Functionality,
        testcase: Int
    ) {
        val result = functionality.getArmstrongNums1(testcase)
        val expected = getArmstrongNums(testcase)

        assert(result == expected) {
            "Wrong output \"$result\" for $testcase, expected \"$expected\""
        }
    }

    internal fun getArmstrongNums(upTo: Int): List<Int> {
        return buildList {
            for (i in 0..upTo) {
                if (ArmstrongNumbersTest.isArmstrong(i)) {
                    add(i)
                }
            }
        }
    }

}
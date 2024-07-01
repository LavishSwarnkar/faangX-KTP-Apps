package com.faangx.ktp.test

import com.faangx.ktp.basics.LcmHcfCalculatorV1Functionality
import ksp.MiniAppTest

class LcmHcfCalculatorV1Testcase(
    val nums: List<Int>, val lcm: Int, val hcf: Int
)

@MiniAppTest
object LcmHcfCalculatorV1Test {

    fun test(
        functionality: LcmHcfCalculatorV1Functionality,
        testcase: LcmHcfCalculatorV1Testcase
    ) {
        val lcm = functionality.getLCM1(testcase.nums)
        val hcf = functionality.getHCF1(testcase.nums)

        assert(lcm == testcase.lcm) {
            "Wrong LCM $lcm for ${testcase.nums}, expected ${testcase.lcm}"
        }
        assert(hcf == testcase.hcf) {
            "Wrong HCF $hcf for ${testcase.nums}, expected ${testcase.hcf}"
        }
    }

    internal fun getLCM(nums: List<Int>): Int {
        var lcm = LcmHcfCalculatorTest.getLCM(nums[0], nums[1])
        for (i in 2..nums.lastIndex) {
            lcm = LcmHcfCalculatorTest.getLCM(lcm, nums[i])
        }
        return lcm
    }

    internal fun getHCF(nums: List<Int>): Int {
        var hcf = LcmHcfCalculatorTest.getHCF(nums[0], nums[1])
        for (i in 2..nums.lastIndex) {
            hcf = LcmHcfCalculatorTest.getHCF(hcf, nums[i])
        }
        return hcf
    }

    fun testcases(): List<LcmHcfCalculatorV1Testcase> {
        return buildList {
            repeat(10) {
//                val x = Random.nextInt(100) + 1
//                val y = Random.nextInt(100) + 1
//                add(
//                    LcmHcfCalculatorTestcase(
//                        x = x, y = y,
//                        lcm = getLCM(x, y),
//                        hcf = getHCF(x, y)
//                    )
//                )
            }
        }
    }

}
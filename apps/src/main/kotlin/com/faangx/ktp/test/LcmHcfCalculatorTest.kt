package com.faangx.ktp.test

import com.faangx.ktp.basics.LcmHcfCalculatorFunctionality
import kotlin.random.Random

class LcmHcfCalculatorTestcase(
    val x: Int, val y: Int, val lcm: Int, val hcf: Int
)

object LcmHcfCalculatorTest {

    fun test(
        functionality: LcmHcfCalculatorFunctionality,
        testcase: LcmHcfCalculatorTestcase
    ) {
        val lcm = functionality.getLCM1(testcase.x, testcase.y)
        val hcf = functionality.getHCF1(testcase.x, testcase.y)

        assert(lcm == testcase.lcm) {
            "Wrong LCM $lcm for (x = ${testcase.x}, y = ${testcase.y}), expected ${testcase.lcm}"
        }
        assert(hcf == testcase.hcf) {
            "Wrong HCF $hcf for (x = ${testcase.x}, y = ${testcase.y}), expected ${testcase.hcf}"
        }
    }

    internal fun getLCM(x: Int, y: Int): Int {
        if (x == 0 || y == 0) {
            return 0
        }
        var lcm = if (x > y) x else y
        while (true) {
            if (lcm % x == 0 && lcm % y == 0) {
                return lcm
            }
            ++lcm
        }
    }

    internal fun getHCF(x: Int, y: Int): Int {
        var a = x
        var b = y

        while (b != 0) {
            val temp = b
            b = a % b
            a = temp
        }

        return a
    }

    fun testcases(): List<LcmHcfCalculatorTestcase> {
        return buildList {
            repeat(10) {
                val x = Random.nextInt(100) + 1
                val y = Random.nextInt(100) + 1
                add(
                    LcmHcfCalculatorTestcase(
                        x = x, y = y,
                        lcm = getLCM(x, y),
                        hcf = getHCF(x, y)
                    )
                )
            }
        }
    }

}
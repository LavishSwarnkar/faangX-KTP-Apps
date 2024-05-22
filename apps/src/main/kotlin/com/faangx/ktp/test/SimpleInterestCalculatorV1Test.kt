package com.faangx.ktp.test

import com.faangx.ktp.basics.SimpleInterestCalculatorV1Functionality
import ksp.MiniAppTest
import java.util.*

class SimpleInterestCalculatorTestcase(
    val p: Float,
    val r: Float,
    val t: Float,
    val i: Float
)

@MiniAppTest
object SimpleInterestCalculatorV1Test {

    fun test(
        functionality: SimpleInterestCalculatorV1Functionality,
        testcase: SimpleInterestCalculatorTestcase
    ) {
        val actual = testcase.run { functionality.calculateInterest1(p, r, t) }
        assert(actual == testcase.i) {
            testcase.run {
                "Incorrect Interest ($actual) for (p=$p, r=$r, t=$t), expected $i"
            }
        }
    }

    fun testcases(): List<SimpleInterestCalculatorTestcase> {
        return buildList {
            repeat(15) {
                val p = Random().nextFloat(100f)
                val r = Random().nextFloat(20f)
                val t = Random().nextFloat(10f)
                add(
                    SimpleInterestCalculatorTestcase(
                        p = p,
                        r = r,
                        t = t,
                        i = p * r * t / 100
                    )
                )
            }
        }
    }

}
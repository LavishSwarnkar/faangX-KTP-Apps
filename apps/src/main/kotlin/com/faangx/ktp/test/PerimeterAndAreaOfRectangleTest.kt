package com.faangx.ktp.test

import com.faangx.ktp.basics.PerimeterAndAreaOfRectAppFunctionality
import ksp.MiniAppTest
import kotlin.random.Random

class PerimeterAndAreaOfRectangleTestCase(
    val l: Int,
    val b: Int,
    val perimeter: Int,
    val area: Int
)

@MiniAppTest
object PerimeterAndAreaOfRectAppTest {

    fun test(
        functionality: PerimeterAndAreaOfRectAppFunctionality,
        testcase: PerimeterAndAreaOfRectangleTestCase
    ) {
        testcase.run {
            val actualPerimeter = functionality.getPerimeterOfRectangle1(l, b)
            assert(perimeter == actualPerimeter) {
                "Wrong perimeter ($actualPerimeter) returned for (l = $l, b = $b), expected $perimeter"
            }
            val actualArea = functionality.getAreaOfRectangle1(l, b)
            assert(area == actualArea) {
                "Wrong area ($actualArea) returned for (l = $l, b = $b), expected $area"
            }
        }
    }

    fun testcases(): List<PerimeterAndAreaOfRectangleTestCase> {
        return buildList {
            repeat(15) {
                val l = Random.nextInt(1000)
                val b = Random.nextInt(1000)
                add(
                    PerimeterAndAreaOfRectangleTestCase(
                        l, b, 2 * (l + b), l * b
                    )
                )
            }
        }
    }

}
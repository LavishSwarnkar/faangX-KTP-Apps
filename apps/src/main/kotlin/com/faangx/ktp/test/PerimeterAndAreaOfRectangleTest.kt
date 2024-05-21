package com.faangx.ktp.test

import com.faangx.ktp.basics.PerimeterAndAreaOfRectAppFunctionality
import ksp.MiniAppTest
import java.util.*

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
            val correctPerimeter = functionality.getPerimeterOfRectangle1(l, b)
            assert(perimeter == correctPerimeter) {
                "Wrong perimeter ($perimeter) returned for (l = $l, b = $b), expected $correctPerimeter"
            }
            val correctArea = functionality.getAreaOfRectangle1(l, b)
            assert(area == correctArea) {
                "Wrong area ($area) returned for (l = $l, b = $b), expected $correctArea"
            }
        }
    }

    fun testcases(): List<PerimeterAndAreaOfRectangleTestCase> {
        return buildList {
            repeat(15) {
                val l = Random().nextInt(1000)
                val b = Random().nextInt(1000)
                add(
                    PerimeterAndAreaOfRectangleTestCase(
                        l, b, 2 * (l + b), l * b
                    )
                )
            }
        }
    }

}
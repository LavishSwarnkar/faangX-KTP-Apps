package com.faangx.ktp.test

import com.faangx.ktp.basics.GradeCalculatorAppFunctionality
import ksp.MiniAppTest

data class GradeCalculatorTestcase(
    val marks: List<Int>,
    val grade: String
)

@MiniAppTest
object GradeCalculatorTest {

    fun test(
        functionality: GradeCalculatorAppFunctionality,
        testcase: GradeCalculatorTestcase
    ) {
        val list = testcase.marks
        val result = functionality.getGrade1(list[0], list[1], list[2], list[3], list[4])
        assert(result== testcase.grade) {
            "Incorrect $result for $list, expected ${testcase.grade}"
        }
    }

    fun testcases(): List<GradeCalculatorTestcase> {
        return listOf(
            GradeCalculatorTestcase(listOf(95, 98, 92, 97, 90), "A+"),
            GradeCalculatorTestcase(listOf(88, 92, 96, 97, 91), "A+"),
            GradeCalculatorTestcase(listOf(85, 88, 90, 89, 87), "A"),
            GradeCalculatorTestcase(listOf(82, 89, 87, 91, 88), "A"),
            GradeCalculatorTestcase(listOf(75, 78, 80, 79, 77), "B"),
            GradeCalculatorTestcase(listOf(79, 81, 77, 83, 85), "A"),
            GradeCalculatorTestcase(listOf(65, 68, 70, 69, 67), "C"),
            GradeCalculatorTestcase(listOf(69, 71, 67, 63, 66), "C"),
            GradeCalculatorTestcase(listOf(55, 58, 60, 59, 57), "D"),
            GradeCalculatorTestcase(listOf(59, 61, 57, 53, 56), "D"),
            GradeCalculatorTestcase(listOf(45, 48, 50, 49, 47), "E"),
            GradeCalculatorTestcase(listOf(49, 51, 47, 43, 46), "E"),
            GradeCalculatorTestcase(listOf(35, 38, 40, 39, 37), "E"),
            GradeCalculatorTestcase(listOf(0, 20, 0, 33, 36), "Fail")
        )
    }

    internal fun getGrade1(m1: Int, m2: Int, m3: Int, m4: Int, m5: Int): String {
        val average = (m1 + m2 + m3 + m4 + m5) / 50f
        return when (average) {
            in 9f..10f -> "A+"
            in 8f..9f -> "A"
            in 7f..8f -> "B"
            in 6f..7f -> "C"
            in 5f..6f -> "D"
            in 3f..5f -> "E"
            else -> "Fail"
        }
    }





    fun getGrade(m1: Int, m2: Int, m3: Int, m4: Int, m5: Int): String {
        val average = (m1 + m2 + m3 + m4 + m5) / 50f
        return when (average) {
            in 9f..10f -> "A+"
            in 8f..9f -> "A"
            in 7f..8f -> "B"
            in 6f..7f -> "C"
            in 5f..6f -> "D"
            in 3f..5f -> "E"
            else -> "Fail"
        }
    }





}
package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.GradeCalculatorAppFunctionality
import com.faangx.ktp.basics.GradeCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.GradeCalculatorTest
import com.faangx.ktp.test.GradeCalculatorTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class GradeCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: GradeCalculatorTestcase) {
        GradeCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                GradeCalculator_MobileMiniApp().simpleName()
            ) as GradeCalculatorAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<GradeCalculatorTestcase> {
            return GradeCalculatorTest.testcases()
        }
    }

}
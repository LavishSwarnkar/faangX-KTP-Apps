package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.FactorialCalculatorAppFunctionality
import com.faangx.ktp.basics.FactorialCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.FactorialCalculatorTest
import com.faangx.ktp.test.FactorialCalculatorTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class FactorialCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: FactorialCalculatorTestcase) {
        FactorialCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                FactorialCalculator_MobileMiniApp().simpleName()
            ) as FactorialCalculatorAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<FactorialCalculatorTestcase> {
            return FactorialCalculatorTest.testcases()
        }
    }

}
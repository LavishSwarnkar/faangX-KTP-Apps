package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.FactorCalculatorFunctionality
import com.faangx.ktp.basics.FactorCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.FactorCalculatorTest
import com.faangx.ktp.test.FactorCalculatorTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class FactorCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: FactorCalculatorTestcase) {
        FactorCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                FactorCalculator_MobileMiniApp().simpleName()
            ) as FactorCalculatorFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<FactorCalculatorTestcase> {
            return FactorCalculatorTest.testcases()
        }
    }

}
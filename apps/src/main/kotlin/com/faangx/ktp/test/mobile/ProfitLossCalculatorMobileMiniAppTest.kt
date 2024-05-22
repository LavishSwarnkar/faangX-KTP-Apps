package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.ProfitLossCalculatorFunctionality
import com.faangx.ktp.basics.ProfitLossCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.ProfitLossCalculatorTest
import com.faangx.ktp.test.ProfitLossCalculatorTestCase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ProfitLossCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: ProfitLossCalculatorTestCase) {
        ProfitLossCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                ProfitLossCalculator_MobileMiniApp().simpleName()
            ) as ProfitLossCalculatorFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<ProfitLossCalculatorTestCase> {
            return ProfitLossCalculatorTest.testcases()
        }
    }

}
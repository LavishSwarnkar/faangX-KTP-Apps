package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.FactorCalculatorFunctionality
import com.faangx.ktp.basics.FactorCalculatorV1Functionality
import com.faangx.ktp.basics.FactorCalculatorV1_MobileMiniApp
import com.faangx.ktp.basics.FactorCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.FactorCalculatorTest
import com.faangx.ktp.test.FactorCalculatorTestcase
import com.faangx.ktp.test.FactorCalculatorV1Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class FactorCalculatorV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: FactorCalculatorTestcase) {
        FactorCalculatorV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                FactorCalculatorV1_MobileMiniApp().simpleName()
            ) as FactorCalculatorV1Functionality,
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
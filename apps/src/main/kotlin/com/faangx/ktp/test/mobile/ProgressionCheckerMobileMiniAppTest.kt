package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.FactorCalculatorFunctionality
import com.faangx.ktp.basics.FactorCalculator_MobileMiniApp
import com.faangx.ktp.basics.ProgressionCheckerFunctionality
import com.faangx.ktp.basics.ProgressionChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.FactorCalculatorTest
import com.faangx.ktp.test.FactorCalculatorTestcase
import com.faangx.ktp.test.ProgressionCheckerTest
import com.faangx.ktp.test.ProgressionCheckerTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ProgressionCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: ProgressionCheckerTestcase) {
        ProgressionCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                ProgressionChecker_MobileMiniApp().simpleName()
            ) as ProgressionCheckerFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<ProgressionCheckerTestcase> {
            return ProgressionCheckerTest.testcases()
        }
    }

}
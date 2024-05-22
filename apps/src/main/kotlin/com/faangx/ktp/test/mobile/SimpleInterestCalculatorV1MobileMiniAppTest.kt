package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.SimpleInterestCalculatorV1Functionality
import com.faangx.ktp.basics.SimpleInterestCalculatorV1_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.SimpleInterestCalculatorV1Test
import com.faangx.ktp.test.SimpleInterestCalculatorTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SimpleInterestCalculatorV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: SimpleInterestCalculatorTestcase) {
        SimpleInterestCalculatorV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                SimpleInterestCalculatorV1_MobileMiniApp().simpleName()
            ) as SimpleInterestCalculatorV1Functionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<SimpleInterestCalculatorTestcase> {
            return SimpleInterestCalculatorV1Test.testcases()
        }
    }

}
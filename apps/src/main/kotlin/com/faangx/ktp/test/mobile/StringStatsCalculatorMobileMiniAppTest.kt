package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.StringStatsCalculatorFunctionality
import com.faangx.ktp.basics.StringStatsCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.StringStatsCalculatorTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class StringStatsCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: String) {
        StringStatsCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                StringStatsCalculator_MobileMiniApp().simpleName()
            ) as StringStatsCalculatorFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<String> {
            return StringStatsCalculatorTest.testcases()
        }
    }

}
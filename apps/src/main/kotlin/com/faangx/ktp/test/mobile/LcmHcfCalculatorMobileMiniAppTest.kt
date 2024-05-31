package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.LcmHcfCalculatorFunctionality
import com.faangx.ktp.basics.LcmHcfCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.LcmHcfCalculatorTest
import com.faangx.ktp.test.LcmHcfCalculatorTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class LcmHcfCalculatorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: LcmHcfCalculatorTestcase) {
        LcmHcfCalculatorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                LcmHcfCalculator_MobileMiniApp().simpleName()
            ) as LcmHcfCalculatorFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<LcmHcfCalculatorTestcase> {
            return LcmHcfCalculatorTest.testcases()
        }
    }

}
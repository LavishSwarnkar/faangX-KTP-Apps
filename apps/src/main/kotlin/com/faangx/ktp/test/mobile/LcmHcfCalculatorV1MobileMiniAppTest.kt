package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.LcmHcfCalculatorFunctionality
import com.faangx.ktp.basics.LcmHcfCalculatorV1Functionality
import com.faangx.ktp.basics.LcmHcfCalculatorV1_MobileMiniApp
import com.faangx.ktp.basics.LcmHcfCalculator_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.LcmHcfCalculatorTest
import com.faangx.ktp.test.LcmHcfCalculatorTestcase
import com.faangx.ktp.test.LcmHcfCalculatorV1Test
import com.faangx.ktp.test.LcmHcfCalculatorV1Testcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class LcmHcfCalculatorV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: LcmHcfCalculatorV1Testcase) {
        LcmHcfCalculatorV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                LcmHcfCalculatorV1_MobileMiniApp().simpleName()
            ) as LcmHcfCalculatorV1Functionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<LcmHcfCalculatorV1Testcase> {
            return LcmHcfCalculatorV1Test.testcases()
        }
    }

}
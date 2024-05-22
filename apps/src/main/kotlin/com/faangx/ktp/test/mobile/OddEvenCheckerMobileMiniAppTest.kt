package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.OddEvenCheckerFunctionality
import com.faangx.ktp.basics.OddEvenChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.OddEvenCheckerTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias OddEvenCheckerTestCase = Pair<Int, String>

class OddEvenCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: OddEvenCheckerTestCase) {
        OddEvenCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                OddEvenChecker_MobileMiniApp().simpleName()
            ) as OddEvenCheckerFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<OddEvenCheckerTestCase> {
            return OddEvenCheckerTest.testcases()
        }
    }

}
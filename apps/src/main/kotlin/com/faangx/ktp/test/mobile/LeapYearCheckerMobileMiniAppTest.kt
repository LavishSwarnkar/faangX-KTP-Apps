package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.LeapYearCheckerFunctionality
import com.faangx.ktp.basics.LeapYearChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.LeapYearCheckerTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias LeapYearCheckerTestCase = Pair<Int, Boolean>

class LeapYearCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: LeapYearCheckerTestCase) {
        LeapYearCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                LeapYearChecker_MobileMiniApp().simpleName()
            ) as LeapYearCheckerFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<LeapYearCheckerTestCase> {
            return LeapYearCheckerTest.testcases()
        }
    }

}
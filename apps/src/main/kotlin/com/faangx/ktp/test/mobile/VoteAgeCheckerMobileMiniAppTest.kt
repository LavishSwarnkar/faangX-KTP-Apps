package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.VoteAgeCheckerFunctionality
import com.faangx.ktp.basics.VoteAgeChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.VoteAgeCheckerTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias VoteAgeCheckerTestCase = Pair<Int, Boolean>

class VoteAgeCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: VoteAgeCheckerTestCase) {
        VoteAgeCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                VoteAgeChecker_MobileMiniApp().simpleName()
            ) as VoteAgeCheckerFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<VoteAgeCheckerTestCase> {
            return VoteAgeCheckerTest.testcases()
        }
    }

}
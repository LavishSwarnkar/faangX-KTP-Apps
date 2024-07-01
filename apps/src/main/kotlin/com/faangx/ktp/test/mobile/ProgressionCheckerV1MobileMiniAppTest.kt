package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.*
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ProgressionCheckerV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: ProgressionCheckerTestcase) {
        ProgressionCheckerV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                ProgressionCheckerV1_MobileMiniApp().simpleName()
            ) as ProgressionCheckerV1Functionality,
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
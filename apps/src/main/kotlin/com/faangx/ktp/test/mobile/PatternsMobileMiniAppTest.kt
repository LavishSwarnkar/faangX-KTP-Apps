package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.PatternsAppFunctionality
import com.faangx.ktp.basics.Patterns_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.PatternsTest
import com.faangx.ktp.test.PatternsTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PatternsMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: PatternsTestcase) {
        PatternsTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                Patterns_MobileMiniApp().simpleName()
            ) as PatternsAppFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<PatternsTestcase> {
            return PatternsTest.testcases()
        }
    }

}
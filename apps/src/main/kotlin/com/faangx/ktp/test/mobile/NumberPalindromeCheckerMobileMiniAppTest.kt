package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.NumberPalindromeCheckerFunctionality
import com.faangx.ktp.basics.NumberPalindromeChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.NumberPalindromeCheckerTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class NumberPalindromeCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Long) {
        NumberPalindromeCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                NumberPalindromeChecker_MobileMiniApp().simpleName()
            ) as NumberPalindromeCheckerFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<Long> {
            return NumberPalindromeCheckerTest.testcases()
        }
    }

}
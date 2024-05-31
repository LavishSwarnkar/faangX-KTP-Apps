package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.StringPalindromeCheckerFunctionality
import com.faangx.ktp.basics.StringPalindromeChecker_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.StringPalindromeCheckerTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class StringPalindromeCheckerMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: String) {
        StringPalindromeCheckerTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                StringPalindromeChecker_MobileMiniApp().simpleName()
            ) as StringPalindromeCheckerFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<String> {
            return StringPalindromeCheckerTest.testcases()
        }
    }

}
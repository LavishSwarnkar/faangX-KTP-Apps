package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.GreetingAppFunctionality
import com.faangx.ktp.basics.Greeting_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.GreetingTest
import com.faangx.ktp.test.GreetingAppTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class GreetingMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: GreetingAppTestcase) {
        GreetingTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                Greeting_MobileMiniApp().simpleName()
            ) as GreetingAppFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<GreetingAppTestcase> {
            return GreetingTest.testcases()
        }
    }

}
package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.MaxOfThreeNums_MobileMiniApp
import com.faangx.ktp.basics.MaxOfTwoNumsAppFunctionality
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.MaxOfTwoNumsTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias MaxOfTwoNumsTestCase = Triple<Int, Int, Int>

class MaxOfTwoNumsMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: MaxOfTwoNumsTestCase) {
        MaxOfTwoNumsTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                MaxOfThreeNums_MobileMiniApp().simpleName()
            ) as MaxOfTwoNumsAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<MaxOfTwoNumsTestCase> {
            return MaxOfTwoNumsTest.testcases()
        }
    }

}
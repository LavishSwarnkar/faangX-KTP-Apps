package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.MaxOfThreeNumsAppFunctionality
import com.faangx.ktp.basics.MaxOfThreeNums_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.MaxOfThreeNumsTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias MaxOfThreeNumsTestCase = Pair<Triple<Int, Int, Int>, Int>

class MaxOfThreeNumsMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: MaxOfThreeNumsTestCase) {
        MaxOfThreeNumsTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                MaxOfThreeNums_MobileMiniApp().simpleName()
            ) as MaxOfThreeNumsAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<MaxOfThreeNumsTestCase> {
            return MaxOfThreeNumsTest.testcases()
        }
    }

}
package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.StageOfLifeAppFunctionality
import com.faangx.ktp.basics.StageOfLife_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.StageOfLifeTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

typealias StageOfLifeTestCase = Pair<Int, String>

class StageOfLifeMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: StageOfLifeTestCase) {
        StageOfLifeTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                StageOfLife_MobileMiniApp().simpleName()
            ) as StageOfLifeAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<StageOfLifeTestCase> {
            return StageOfLifeTest.testcases()
        }
    }

}
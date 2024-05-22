package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.MultiplicationTableAppFunctionality
import com.faangx.ktp.basics.MultiplicationTableAppV1Functionality
import com.faangx.ktp.basics.MultiplicationTableV1_MobileMiniApp
import com.faangx.ktp.basics.MultiplicationTable_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.MultiplicationTableTest
import com.faangx.ktp.test.MultiplicationTableTestCase
import com.faangx.ktp.test.MultiplicationTableV1Test
import com.faangx.ktp.test.MultiplicationTableV1TestCase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MultiplicationTableV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: MultiplicationTableV1TestCase) {
        MultiplicationTableV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                MultiplicationTableV1_MobileMiniApp().simpleName()
            ) as MultiplicationTableAppV1Functionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<MultiplicationTableV1TestCase> {
            return MultiplicationTableV1Test.testcases()
        }
    }

}
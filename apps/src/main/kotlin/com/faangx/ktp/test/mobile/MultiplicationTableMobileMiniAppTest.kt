package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.MultiplicationTableAppFunctionality
import com.faangx.ktp.basics.MultiplicationTable_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.MultiplicationTableTest
import com.faangx.ktp.test.MultiplicationTableTestCase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MultiplicationTableMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: MultiplicationTableTestCase) {
        MultiplicationTableTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                MultiplicationTable_MobileMiniApp().simpleName()
            ) as MultiplicationTableAppFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<MultiplicationTableTestCase> {
            return MultiplicationTableTest.testcases()
        }
    }

}
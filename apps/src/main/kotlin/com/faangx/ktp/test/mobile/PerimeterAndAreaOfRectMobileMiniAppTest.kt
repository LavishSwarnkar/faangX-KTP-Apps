package com.faangx.ktp.test.mobile

import com.faangx.ktp.basics.PerimeterAndAreaOfRectAppFunctionality
import com.faangx.ktp.basics.PerimeterAndAreaOfRect_MobileMiniApp
import com.faangx.ktp.test.PerimeterAndAreaOfRectAppTest
import com.faangx.ktp.test.PerimeterAndAreaOfRectangleTestCase
import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.simpleName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PerimeterAndAreaOfRectMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: PerimeterAndAreaOfRectangleTestCase) {
        PerimeterAndAreaOfRectAppTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                PerimeterAndAreaOfRect_MobileMiniApp().simpleName()
            ) as PerimeterAndAreaOfRectAppFunctionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<PerimeterAndAreaOfRectangleTestCase> {
            return PerimeterAndAreaOfRectAppTest.testcases()
        }
    }

}
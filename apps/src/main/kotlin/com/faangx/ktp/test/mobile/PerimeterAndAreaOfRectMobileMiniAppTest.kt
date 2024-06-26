package com.faangx.ktp.test.mobile

import com.faangx.ktp.basics.PerimeterAndAreaOfRectAppFunctionality
import com.faangx.ktp.basics.PerimeterAndAreaOfRect_MobileMiniApp
import com.faangx.ktp.test.PerimeterAndAreaOfRectTest
import com.faangx.ktp.test.PerimeterAndAreaOfRectangleTestCase
import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.simpleName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PerimeterAndAreaOfRectMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testcase: PerimeterAndAreaOfRectangleTestCase) {
        PerimeterAndAreaOfRectTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                PerimeterAndAreaOfRect_MobileMiniApp().simpleName()
            ) as PerimeterAndAreaOfRectAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<PerimeterAndAreaOfRectangleTestCase> {
            return PerimeterAndAreaOfRectTest.testcases()
        }
    }

}
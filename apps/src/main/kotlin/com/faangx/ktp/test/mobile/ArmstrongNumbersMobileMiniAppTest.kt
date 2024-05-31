package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.ArmstrongNumbersFunctionality
import com.faangx.ktp.basics.ArmstrongNumbers_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.ArmstrongNumbersTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ArmstrongNumbersMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Int) {
        ArmstrongNumbersTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                ArmstrongNumbers_MobileMiniApp().simpleName()
            ) as ArmstrongNumbersFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<Int> {
            return ArmstrongNumbersTest.testcases()
        }
    }

}
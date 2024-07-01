package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.ArmstrongNumbersFunctionality
import com.faangx.ktp.basics.ArmstrongNumbersV1Functionality
import com.faangx.ktp.basics.ArmstrongNumbersV1_MobileMiniApp
import com.faangx.ktp.basics.ArmstrongNumbers_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.ArmstrongNumbersTest
import com.faangx.ktp.test.ArmstrongNumbersV1Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ArmstrongNumbersV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Int) {
        ArmstrongNumbersV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                ArmstrongNumbersV1_MobileMiniApp().simpleName()
            ) as ArmstrongNumbersV1Functionality,
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
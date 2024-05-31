package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.loops.SpiralAppFunctionality
import com.faangx.ktp.basics.loops.Spiral_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.SpiralTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SpiralMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Int) {
        SpiralTest.test(
            spiralAppFunctionality = MiniAppFunctionalityHelper.getFunctionality(
                Spiral_MobileMiniApp().simpleName()
            ) as SpiralAppFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<Int> {
            return SpiralTest.testcases()
        }
    }

}
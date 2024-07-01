package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.SpiralAppFunctionality
import com.faangx.ktp.basics.SpiralV1AppFunctionality
import com.faangx.ktp.basics.SpiralV1_MobileMiniApp
import com.faangx.ktp.basics.Spiral_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.SpiralTest
import com.faangx.ktp.test.SpiralV1Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SpiralV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Int) {
        SpiralV1Test.test(
            spiralAppFunctionality = MiniAppFunctionalityHelper.getFunctionality(
                SpiralV1_MobileMiniApp().simpleName()
            ) as SpiralV1AppFunctionality,
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
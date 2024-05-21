package com.faangx.ktp.test.mobile

import com.faangx.ktp.basics.SquareOfNumAppV1Functionality
import com.faangx.ktp.basics.SquareOfNumV1_MobileMiniApp
import com.faangx.ktp.test.SquareOfNumAppV1Test
import com.faangx.ktp.test.SquareOfNumTestcase
import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.simpleName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

class SquareOfNumV1MobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("TestCases")
    fun Test(testCase: SquareOfNumTestcase) {
        SquareOfNumAppV1Test.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                SquareOfNumV1_MobileMiniApp().simpleName()
            ) as SquareOfNumAppV1Functionality,
            testcase = testCase
        )
    }

    companion object {
        @JvmStatic
        fun TestCases(): List<SquareOfNumTestcase> {
            return buildList {
                repeat(15) {
                    val x = Random.nextLong(9999L)
                    add(x to x * x)
                }
            }
        }
    }

}
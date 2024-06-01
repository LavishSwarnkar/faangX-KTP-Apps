package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.BinomialExpansionFunctionality
import com.faangx.ktp.basics.BinomialExpansion_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.BinomialExpansionTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class BinomialExpansionMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: Int) {
        BinomialExpansionTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                BinomialExpansion_MobileMiniApp().simpleName()
            ) as BinomialExpansionFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<Int> {
            return BinomialExpansionTest.testcases()
        }
    }

}
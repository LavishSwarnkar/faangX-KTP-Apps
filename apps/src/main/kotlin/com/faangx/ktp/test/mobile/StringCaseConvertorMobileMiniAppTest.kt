package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.StringCaseConvertorFunctionality
import com.faangx.ktp.basics.StringCaseConvertor_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.StringCaseConvertorTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class StringCaseConvertorMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: String) {
        StringCaseConvertorTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                StringCaseConvertor_MobileMiniApp().simpleName()
            ) as StringCaseConvertorFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<String> {
            return StringCaseConvertorTest.testcases()
        }
    }

}
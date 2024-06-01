package com.faangx.ktp.test.mobile

import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.basics.CeaserCipherFunctionality
import com.faangx.ktp.basics.CeaserCipher_MobileMiniApp
import com.faangx.ktp.simpleName
import com.faangx.ktp.test.CeaserCipherTest
import com.faangx.ktp.test.CeaserCipherTestcase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class CeaserCipherMobileMiniAppTest {

    @ParameterizedTest
    @MethodSource("Testcases")
    fun Test(testcase: CeaserCipherTestcase) {
        CeaserCipherTest.test(
            functionality = MiniAppFunctionalityHelper.getFunctionality(
                CeaserCipher_MobileMiniApp().simpleName()
            ) as CeaserCipherFunctionality,
            testcase = testcase
        )
    }

    companion object {
        @JvmStatic
        fun Testcases(): List<CeaserCipherTestcase> {
            return CeaserCipherTest.testcases()
        }
    }

}
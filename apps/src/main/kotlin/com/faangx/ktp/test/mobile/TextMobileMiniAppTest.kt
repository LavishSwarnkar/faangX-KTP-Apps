package com.faangx.ktp.test.mobile

import com.faangx.ktp.basics.TextAppFunctionality
import com.faangx.ktp.basics.Text_MobileMiniApp
import com.faangx.ktp.test.TextTest
import com.faangx.ktp.MiniAppFunctionalityHelper
import com.faangx.ktp.simpleName
import org.junit.jupiter.api.Test

class TextMobileMiniAppTest {

    @Test fun test() {
        TextTest.test(
            MiniAppFunctionalityHelper.getFunctionality(
                Text_MobileMiniApp().simpleName()
            ) as TextAppFunctionality
        )
    }

}
package com.faangx.ktp.test

import com.faangx.ktp.basics.TextAppFunctionality
import ksp.MiniAppTest

@MiniAppTest
object TextAppTest {
    fun test(functionality: TextAppFunctionality) {
        assert(functionality.getText1() == "Namaste Kotlin") {
            "Required text is not returned by the function"
        }
    }
}
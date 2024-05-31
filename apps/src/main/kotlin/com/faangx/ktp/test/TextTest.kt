package com.faangx.ktp.test

import com.faangx.ktp.basics.TextAppFunctionality
import ksp.MiniAppTest

@MiniAppTest
object TextTest {
    fun test(functionality: TextAppFunctionality) {
        assert(functionality.getText1() == "Namaste Kotlin") {
            "Required text is not returned by the function"
        }
    }

    internal fun getText(): String {
        return "Namaste Kotlin"
    }
}
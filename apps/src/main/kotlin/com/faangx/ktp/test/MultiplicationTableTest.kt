package com.faangx.ktp.test

import com.faangx.ktp.basics.MultiplicationTableAppFunctionality
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest
import java.util.*

data class MultiplicationTableTestCase(
    val num: Int,
    val table: String
)

@MiniAppTest
object MultiplicationTableTest {

    fun test(
        functionality: MultiplicationTableAppFunctionality,
        testcase: MultiplicationTableTestCase
    ) {
        val actual = captureStdOutput {
            functionality.printTable1(testcase.num)
        }

        assert(actual == testcase.table) {
            "Wrong table (${actual}) printed for ${testcase.num}, " +
                    "expected (${testcase.table})"
        }
    }

    fun testcases(): List<MultiplicationTableTestCase> {
        return buildList {
            repeat(10) {
                val num = Random().nextInt(100)
                val table = buildString {
                    repeat(10) {
                        append("$num X ${it + 1} = ${num * (it + 1)}\n")
                    }
                }
                add(MultiplicationTableTestCase(num, table))
            }
        }
    }

    internal fun printTable(num: Int) {
        repeat(10) {
            println("$num X ${it + 1} = ${num * (it + 1)}")
        }
    }

}
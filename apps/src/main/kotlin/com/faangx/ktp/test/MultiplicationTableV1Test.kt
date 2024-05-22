package com.faangx.ktp.test

import com.faangx.ktp.basics.MultiplicationTableAppV1Functionality
import com.faangx.ktp.util.captureStdOutput
import ksp.MiniAppTest
import java.util.*

data class MultiplicationTableV1TestCase(
    val num: Int,
    val start: Int,
    val end: Int,
    val table: String
)

@MiniAppTest
object MultiplicationTableV1Test {

    fun test(
        functionality: MultiplicationTableAppV1Functionality,
        testcase: MultiplicationTableV1TestCase
    ) {
        val actual = captureStdOutput {
            testcase.run {
                functionality.printTable1(num, start, end)
            }
        }

        assert(actual == testcase.table) {
            "Wrong table ($actual) printed for ${testcase.num}, " +
                    "expected (${testcase.table})"
        }
    }

    fun testcases(): List<MultiplicationTableV1TestCase> {
        return buildList {
            repeat(10) {
                val num = Random().nextInt(100)
                val start = Random().nextInt(100)
                val end = start + 10

                val table = buildString {
                    for (i in start..end) {
                        append("$num X $i = ${num * i}\n")
                    }
                }
                add(MultiplicationTableV1TestCase(num, start, end, table))
            }
        }
    }

}
package com.faangx.ktp.test

import com.faangx.ktp.basics.ProfitLossCalculatorFunctionality
import ksp.MiniAppTest

data class ProfitLossCalculatorTestCase(
    val cp: Int,
    val pl: Int,
    val absPL: Int,
    val sp: Int
)

@MiniAppTest
object ProfitLossCalculatorTest {

    fun test(
        functionality: ProfitLossCalculatorFunctionality,
        testcase: ProfitLossCalculatorTestCase
    ) {
        testcase.run {
            var result = functionality.getSp11(cp, pl)
            assert(result == sp) {
                "Wrong Sp($result) for (cp=$cp, pl=$pl), expected $sp"
            }
            result = functionality.getSp21(cp, absPL)
            assert(result == sp) {
                "Wrong Sp($result) for (cp=$cp, absPL=$absPL), expected $sp"
            }
            result = functionality.getCp11(sp, pl)
            assert(result == cp) {
                "Wrong Cp($result) for (sp=$sp, pl=$pl), expected $cp"
            }
            result = functionality.getCp21(sp, absPL)
            assert(result == cp) {
                "Wrong Cp($result) for (sp=$sp, absPL=$absPL), expected $cp"
            }
            result = functionality.getPl11(cp, sp)
            assert(result == pl) {
                "Wrong Pl($result) for (cp=$cp, sp=$sp), expected $pl"
            }
            result = functionality.getPl21(cp, absPL)
            assert(result == pl) {
                "Wrong Pl($result) for (cp=$cp, absPL=$absPL), expected $pl"
            }
            result = functionality.getPl31(sp, absPL)
            assert(result == pl) {
                "Wrong Pl($result) for (sp=$sp, absPL=$absPL), expected $pl"
            }
            result = functionality.getAbsPL11(cp, sp)
            assert(result == absPL) {
                "Wrong AbsPL($result) for (cp=$cp, sp=$sp), expected $absPL"
            }
            result = functionality.getAbsPL21(cp, pl)
            assert(result == absPL) {
                "Wrong AbsPL($result) for (cp=$cp, pl=$pl), expected $absPL"
            }
            result = functionality.getAbsPL31(sp, pl)
            assert(result == absPL) {
                "Wrong AbsPL($result) for (sp=$sp, pl=$pl), expected $absPL"
            }
        }
    }

    fun testcases(): List<ProfitLossCalculatorTestCase> {
        return buildList {
            for (cp in listOf(10, 100, 200, 500)) {
                for (pl in listOf(1, 5, 10, 50)) {
                    val absPL = cp * pl / 100
                    val sp = cp + absPL
                    add(
                        ProfitLossCalculatorTestCase(cp, if (absPL == 0) 0 else pl, absPL, sp)
                    )
                }
            }
        }
    }

}
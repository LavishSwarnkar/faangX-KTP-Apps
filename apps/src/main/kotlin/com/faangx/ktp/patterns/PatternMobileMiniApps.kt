package com.faangx.ktp.patterns

import com.faangx.ktp.patterns.functionality.PatternLinesAndCharBased_MobileMiniApp
import com.faangx.ktp.patterns.functionality.PatternLinesAndTwoCharsBased_MobileMiniApp
import com.faangx.ktp.patterns.functionality.PatternLinesBased_MobileMiniApp
import com.faangx.ktp.patterns.test.*

fun Pattern_BL_MobileMiniApp() = PatternLinesAndCharBased_MobileMiniApp(
    patternCode = "BL", testClass = Pattern_BL_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_BL"
)

fun Pattern_BR_MobileMiniApp() = PatternLinesAndCharBased_MobileMiniApp(
    patternCode = "BR", testClass = Pattern_BR_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_BR"
)

fun Pattern_TLN_MobileMiniApp() = PatternLinesBased_MobileMiniApp(
    patternCode = "TLN", testClass = Pattern_TLN_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_TLN"
)

fun Pattern_TRSR_MobileMiniApp() = PatternLinesBased_MobileMiniApp(
    patternCode = "TRSR", testClass = Pattern_TRSR_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_TRSR"
)

fun Pattern_PU1_MobileMiniApp() = PatternLinesAndCharBased_MobileMiniApp(
    patternCode = "PU1", testClass = Pattern_PU1_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_PU1"
)

fun Pattern_PU2_MobileMiniApp() = PatternLinesAndTwoCharsBased_MobileMiniApp(
    patternCode = "PU2", testClass = Pattern_PU2_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_PU2"
)

fun Pattern_PD3_MobileMiniApp() = PatternLinesAndTwoCharsBased_MobileMiniApp(
    patternCode = "PD3", testClass = Pattern_PD3_Test::class.java,
    repoPath = "ProgrammingFundamentals/Ep3/Pattern_PD3"
)
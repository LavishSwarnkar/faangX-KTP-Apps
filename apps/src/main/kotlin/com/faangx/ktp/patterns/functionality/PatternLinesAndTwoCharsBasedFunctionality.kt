package com.faangx.ktp.patterns.functionality

import androidx.compose.runtime.Composable
import com.faangx.ktp.MobileMiniApp
import com.faangx.ktp.patterns.single.PatternLinesAndTwoCharsBasedApp
import com.faangx.ktp.test.mobile.FactorCalculatorMobileMiniAppTest
import kotlin.Int
import kotlin.String

interface PatternLinesAndTwoCharsBasedFunctionality {
	fun printPattern1(lines: Int, char1: Char, char2: Char)
}

@Composable
fun PatternLinesAndTwoCharsBasedApp(
	patternCode: String,
	functionality: PatternLinesAndTwoCharsBasedFunctionality
) {
	PatternLinesAndTwoCharsBasedApp(
		patternCode,
		functionality::printPattern1
	)
}

fun PatternLinesAndTwoCharsBased_MobileMiniApp(
	patternCode: String,
	repoPath: String,
	testClass: Class<*>
): MobileMiniApp<PatternLinesAndTwoCharsBasedFunctionality> =
	MobileMiniApp(
		label = "Pattern - $patternCode",
		functionalityClass = PatternLinesAndTwoCharsBasedFunctionality::class.java,
		functionalityInterface = patternLinesAndTwoCharsBasedFunctionality_Interface_AsString(),
		functionalityImpl = patternLinesAndTwoCharsBasedFunctionality_Impl_AsString(),
		functionalityFuns = patternLinesAndTwoCharsBasedFunctionality_Funs_AsString(),
		functionalityImplClassName = "PatternLinesAndTwoCharsBasedFunctionalityImpl",
		testClass = testClass,
		packageName = "com.faangx.ktp.patterns.functionality",
		repoPath = repoPath,
		composable = { PatternLinesAndTwoCharsBasedApp(patternCode, it::printPattern1) }
	)

fun patternLinesAndTwoCharsBasedFunctionality_Interface_AsString(): String =
	"""
|interface PatternLinesAndTwoCharsBasedFunctionality {
|	fun printPattern1(lines: Int, char1: Char, char2: Char)
|}
"""
		.trimMargin()

fun patternLinesAndTwoCharsBasedFunctionality_Impl_AsString(): String =
	"""
|class PatternLinesAndTwoCharsBasedFunctionalityImpl : PatternLinesAndTwoCharsBasedFunctionality {
|	override fun printPattern1(lines: Int, char1: Char, char2: Char) = printPattern(lines, char1, char2)
|}
"""
		.trimMargin()

fun patternLinesAndTwoCharsBasedFunctionality_Funs_AsString(): String =
	"""
|fun printPattern(lines: Int, char1: Char, char2: Char) {
|	TODO()
|}
"""
		.trimMargin()

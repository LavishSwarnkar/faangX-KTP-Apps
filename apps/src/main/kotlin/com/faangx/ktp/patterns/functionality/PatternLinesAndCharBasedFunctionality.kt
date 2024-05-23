package com.faangx.ktp.patterns.functionality

import androidx.compose.runtime.Composable
import com.faangx.ktp.MobileMiniApp
import com.faangx.ktp.patterns.single.PatternLinesAndCharBasedApp
import com.faangx.ktp.test.mobile.FactorCalculatorMobileMiniAppTest
import kotlin.Int
import kotlin.String

interface PatternLinesAndCharBasedFunctionality {
	fun printPattern1(lines: Int, char: Char)
}

@Composable
fun PatternLinesAndCharBasedApp(
	patternCode: String,
	functionality: PatternLinesAndCharBasedFunctionality
) {
	PatternLinesAndCharBasedApp(
		patternCode,
		functionality::printPattern1
	)
}

fun PatternLinesAndCharBased_MobileMiniApp(
	patternCode: String,
	testClass: Class<*>
): MobileMiniApp<PatternLinesAndCharBasedFunctionality> =
	MobileMiniApp(
		label = "Pattern - $patternCode",
		functionalityClass = PatternLinesAndCharBasedFunctionality::class.java,
		functionalityInterface = patternLinesAndCharBasedFunctionality_Interface_AsString(),
		functionalityImpl = patternLinesAndCharBasedFunctionality_Impl_AsString(),
		functionalityFuns = patternLinesAndCharBasedFunctionality_Funs_AsString(),
		functionalityImplClassName = "PatternLinesAndCharBasedFunctionalityImpl",
		testClass = testClass,
		packageName = "com.faangx.ktp.basics",
		composable = { PatternLinesAndCharBasedApp(patternCode, it::printPattern1) }
	)

fun patternLinesAndCharBasedFunctionality_Interface_AsString(): String =
	"""
|interface PatternLinesAndCharBasedFunctionality {
|	fun printPattern1(lines: Int, char: Char)
|}
"""
		.trimMargin()

fun patternLinesAndCharBasedFunctionality_Impl_AsString(): String =
	"""
|class PatternLinesAndCharBasedFunctionalityImpl : PatternLinesAndCharBasedFunctionality {
|	override fun printPattern1(lines: Int, char: Char) = printPattern(lines)
|}
"""
		.trimMargin()

fun patternLinesAndCharBasedFunctionality_Funs_AsString(): String =
	"""
|fun printPattern(lines: Int, char: Char) {
|	TODO()
|}
"""
		.trimMargin()

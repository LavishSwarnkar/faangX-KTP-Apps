package com.faangx.ktp.patterns.functionality

import androidx.compose.runtime.Composable
import com.faangx.ktp.MobileMiniApp
import com.faangx.ktp.patterns.single.PatternLinesBasedApp
import com.faangx.ktp.test.mobile.FactorCalculatorMobileMiniAppTest
import kotlin.Int
import kotlin.String

interface PatternLinesBasedFunctionality {
	fun printPattern1(lines: Int)
}

@Composable
fun PatternLinesBasedApp(
	patternCode: String,
	functionality: PatternLinesBasedFunctionality
) {
	PatternLinesBasedApp(
		patternCode,
		functionality::printPattern1
	)
}

fun PatternLinesBased_MobileMiniApp(
	patternCode: String,
	testClass: Class<*>
): MobileMiniApp<PatternLinesBasedFunctionality> =
	MobileMiniApp(
		label = "Pattern - $patternCode",
		functionalityClass = PatternLinesBasedFunctionality::class.java,
		functionalityInterface = patternLinesBasedFunctionality_Interface_AsString(),
		functionalityImpl = patternLinesBasedFunctionality_Impl_AsString(),
		functionalityFuns = patternLinesBasedFunctionality_Funs_AsString(),
		functionalityImplClassName = "PatternLinesBasedFunctionalityImpl",
		testClass = testClass,
		packageName = "com.faangx.ktp.basics",
		composable = { PatternLinesBasedApp(patternCode, it::printPattern1) }
	)

fun patternLinesBasedFunctionality_Interface_AsString(): String =
	"""
|interface PatternLinesBasedFunctionality {
|	fun printPattern1(lines: Int)
|}
"""
		.trimMargin()

fun patternLinesBasedFunctionality_Impl_AsString(): String =
	"""
|class PatternLinesBasedFunctionalityImpl : PatternLinesBasedFunctionality {
|	override fun printPattern1(lines: Int) = printPattern(lines)
|}
"""
		.trimMargin()

fun patternLinesBasedFunctionality_Funs_AsString(): String =
	"""
|fun printPattern(lines: Int) {
|	TODO()
|}
"""
		.trimMargin()

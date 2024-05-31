package com.faangx.ktp.patterns.functionality

import androidx.compose.runtime.Composable
import com.faangx.ktp.MobileMiniApp
import com.faangx.ktp.patterns.single.PatternWordBasedApp
import com.faangx.ktp.patterns.test.PatternCorrectImplHelper
import com.faangx.ktp.test.mobile.FactorCalculatorMobileMiniAppTest

interface PatternWordBasedFunctionality {
	fun printPattern1(word: String)
}

@Composable
fun PatternWordBasedApp(
	patternCode: String,
	functionality: PatternWordBasedFunctionality
) {
	PatternWordBasedApp(
		patternCode,
		functionality::printPattern1
	)
}

fun PatternWordBased_MobileMiniApp(
	patternCode: String,
	repoPath: String,
	testClass: Class<*>
): MobileMiniApp<PatternWordBasedFunctionality> =
	MobileMiniApp(
		label = "Pattern - $patternCode",
		functionalityClass = PatternWordBasedFunctionality::class.java,
		functionalityInterface = patternWordBasedFunctionality_Interface_AsString(),
		functionalityImpl = patternWordBasedFunctionality_Impl_AsString(),
		functionalityFuns = patternWordBasedFunctionality_Funs_AsString(),
		functionalityImplClassName = "PatternWordBasedFunctionalityImpl",
		testFunctionality = object : PatternWordBasedFunctionality {
			override fun printPattern1(word: String) = PatternCorrectImplHelper.getForWordBased(patternCode)(word)
		},
		testClass = testClass,
		packageName = "com.faangx.ktp.patterns.functionality",
		repoPath = repoPath,
		supportsParentScroll = false,
		composable = { PatternWordBasedApp(patternCode, it::printPattern1) }
	)

fun patternWordBasedFunctionality_Interface_AsString(): String =
	"""
|interface PatternWordBasedFunctionality {
|	fun printPattern1(word: String)
|}
"""
		.trimMargin()

fun patternWordBasedFunctionality_Impl_AsString(): String =
	"""
|class PatternWordBasedFunctionalityImpl : PatternWordBasedFunctionality {
|	override fun printPattern1(word: String) = printPattern(word)
|}
"""
		.trimMargin()

fun patternWordBasedFunctionality_Funs_AsString(): String =
	"""
|fun printPattern(word: String) {
|	TODO()
|}
"""
		.trimMargin()

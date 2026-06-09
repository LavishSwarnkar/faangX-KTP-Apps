package com.faangx.ktp.catalog

import androidx.compose.runtime.Composable
import com.faangx.ktp.catalog.demo.*
import org.example.com.faangx.ktp.catalog.demo.PatternsAppDemo

enum class MiniApp(
    val title: String,
    val category: String,
    val subcategory: String? = null,
    val demo: @Composable () -> Unit
) {
    /**
     *
     * repeat, for :
     * - Currency Denominations
     */
    SquareOfNum(
        title = "Square Of Number",
        category = "Simple one line Functions",
        demo = { SquareOfNumAppV1Demo() }
    ),
    PerimeterAndAreaOfRectApp(
        title = "Perimeter & Area of Rectangle",
        category = "Simple one line Functions",
        demo = { PerimeterAndAreaOfRectAppDemo() }
    ),
    SimpleInterestCalculator(
        title = "Simple Interest Calculator",
        category = "Simple one line Functions",
        demo = { SimpleInterestCalculatorV1Demo() }
    ),
    ProfitLossCalculator(
        title = "Profit & Loss Calculator",
        category = "Simple one line Functions",
        demo = { ProfitLossCalculatorDemo() }
    ),
    VoteAgeChecker(
        title = "Vote Age Checker",
        category = "Control flow",
        subcategory = "if-else",
        demo = { VoteAgeCheckAppDemo() }
    ),
    OddEvenChecker(
        title = "Odd Even Checker",
        category = "Control flow",
        subcategory = "if-else",
        demo = { OddEvenCheckerDemo() }
    ),
    MaxOfTwoNums(
        title = "Max of two Nums",
        category = "Control flow",
        subcategory = "if-else",
        demo = { MaxOfTwoNumsDemo() }
    ),
    LeapYearChecker(
        title = "Leap Year Checker",
        category = "Control flow",
        subcategory = "if-else",
        demo = { LeapYearCheckerDemo() }
    ),
    MaxOfThreeNums(
        title = "Max of three Nums",
        category = "Control flow",
        subcategory = "if-else-if, when",
        demo = { MaxOfThreeNumsDemo() }
    ),
    StageOfLifeApp(
        title = "Stage of Life",
        category = "Control flow",
        subcategory = "if-else-if, when",
        demo = { StageOfLifeAppDemo() }
    ),
    GradeCalculator(
        title = "Grade Calculator",
        category = "Control flow",
        subcategory = "if-else-if, when",
        demo = { GradeCalculatorDemo() }
    ),
    MultiplicationTableApp(
        title = "Multiplication Table",
        category = "Control flow",
        subcategory = "for loop",
        demo = { MultiplicationTableAppV1Demo() }
    ),
    FactorCalculator(
        title = "Factor Calculator, Prime Checker",
        category = "Control flow",
        subcategory = "for loop",
        demo = { FactorCalculatorDemo() }
    ),
    FactorialCalculator(
        title = "Factorial Calculator",
        category = "Control flow",
        subcategory = "for loop",
        demo = { FactorialCalculatorDemo() }
    ),
    PatternsApp(
        title = "Patterns",
        category = "Control flow",
        subcategory = "for loop",
        demo = { PatternsAppDemo() }
    ),
    AtmSimulatorApp(
        title = "Atm Simulator",
        category = "Control flow",
        subcategory = "for loop",
        demo = { AtmSimulatorDemo() }
    ),
    NumberSystemConverter(
        title = "Number System Converter",
        category = "Control flow",
        subcategory = "while loop",
        demo = { NumberSystemConverterDemo() }
    ),
    LcmHcf(
        title = "LCM HCF Calculator",
        category = "Control flow",
        subcategory = "while loop",
        demo = { LcmHcfCalculatorDemo() }
    ),
    PasswordStrengthChecker(
        title = "Password Strength Checker",
        category = "String",
        demo = { PasswordStrengthCheckerDemo() }
    ),
    CeaserCipher(
        title = "Ceaser's Cipher",
        category = "String",
        demo = { CeaserCipherDemo() }
    ),
    Base64(
        title = "Base64 Converter",
        category = "String",
        demo = { Base64AppDemo() }
    ),
    GreetingApp(
        title = "Greeting App",
        category = "New",
        demo = { GreetingAppDemo() }
    ),
    TextApp(
        title = "Text App",
        category = "New",
        demo = { TextAppDemo() }
    ),
    StringStatsCalculator(
        title = "String Stats Calculator",
        category = "New",
        demo = { StringStatsCalculatorDemo() }
    ),
    StringCaseConvertor(
        title = "String Case Convertor",
        category = "New",
        demo = { StringCaseConvertorDemo() }
    ),
    NumberPalindromeChecker(
        title = "Number Palindrome Checker",
        category = "New",
        demo = { NumberPalindromeCheckerDemo() }
    ),
    StringPalindromeChecker(
        title = "String Palindrome Checker",
        category = "New",
        demo = { StringPalindromeCheckerDemo() }
    ),
    BinomialExpansion(
        title = "Binomial Expansion",
        category = "New",
        demo = { BinomialExpansionDemo() }
    ),
    ArmstrongNumbers(
        title = "Armstrong Numbers",
        category = "New",
        demo = { ArmstrongNumbersDemo() }
    ),
    ProgressionChecker(
        title = "Progression Checker",
        category = "New",
        demo = { ProgressionCheckerDemo() }
    ),
    IntListOperations(
        title = "Int List Operations",
        category = "New",
        demo = { IntListOperationsDemo() }
    ),
    Spiral(
        title = "Spiral",
        category = "New",
        demo = { SpiralDemo() }
    );

    companion object
}
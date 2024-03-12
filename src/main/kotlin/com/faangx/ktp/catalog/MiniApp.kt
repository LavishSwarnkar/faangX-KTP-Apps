package com.faangx.ktp.catalog

import androidx.compose.runtime.Composable
import com.faangx.ktp.catalog.demo.*

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
        demo = { SquareOfNumAppDemo() }
    ),
    PerimeterAndAreaOfRectApp(
        title = "Perimeter & Area of Rectangle",
        category = "Simple one line Functions",
        demo = { PerimeterAndAreaOfRectAppDemo() }
    ),
    SimpleInterestCalculator(
        title = "Simple Interest Calculator",
        category = "Simple one line Functions",
        demo = { SimpleInterestCalculatorDemo() }
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
        subcategory = "if-else-if",
        demo = { MaxOfThreeNumsDemo() }
    ),
    StageOfLifeApp(
        title = "Stage of Life",
        category = "Control flow",
        subcategory = "when",
        demo = { StageOfLifeAppDemo() }
    ),
    GradeCalculator(
        title = "Grade Calculator",
        category = "Control flow",
        subcategory = "when",
        demo = { GradeCalculatorDemo() }
    ),
    MultiplicationTableApp(
        title = "Multiplication Table",
        category = "Control flow",
        subcategory = "for loop",
        demo = { MultiplicationTableAppDemo() }
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
    );

    companion object
}
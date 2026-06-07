# 04 — MiniApps Index

Every MiniApp, what it teaches, and review comments.

**Source of truth for "shipped & in what order":**
`KTP-MiniApps-App/.../catalog/MiniAppsHelper.getAll()`. The 34 apps below are listed in that
exact order. "Functionality" = the function(s) the student must implement. The `repoPath`
maps to a folder (`src/Task.kt` + `task-info.yaml`) in the student's `KTP-Course` git repo,
and its `EpN` segment marks the course episode.

> Legend — **Teaches** = the core concept; **Comment** = authoring/review note.

---

## Episode 2 — Variables, Types, Operators, Conditionals

### 1. Text App  ·  `Ep2/TextApp`
- **Functionality:** `getText(): String`
- **Teaches:** the very first function — return a `String`; pure input→output; the MiniApp idea
  (your function's return value shows on screen).
- **Comment:** Zero parameters, no `paramNames`. The gentlest possible on-ramp.

### 2. Perimeter & Area of Rectangle  ·  `Ep2/PerimeterAndAreaOfRect`
- **Functionality:** `getPerimeterOfRectangle(l, b): Int`, `getAreaOfRectangle(l, b): Int`
- **Teaches:** multiple parameters; arithmetic; **two** functionality functions in one app.
- **Comment:** First multi-function app — good intro to `paramNames` semicolon grouping (`"l, b; l, b"`).

### 3. Square Of a Number (V1)  ·  `Ep2/SquareOfNum`
- **Functionality:** `getSquareOf(num: Long): Long`
- **Teaches:** single numeric transform; `Long` arithmetic/overflow awareness.
- **Comment:** Registered as `SquareOfNumV1_MobileMiniApp`. `SquareOfNumApp` (Int version) wraps
  V1 — a non-shipped warm-up variant.

### 4. Simple Interest Calculator (V1)  ·  `Ep2/SimpleInterestCalculator`
- **Functionality:** `calculateInterest(principal: Float, rate: Float, time: Float): Float`
- **Teaches:** formula translation (P·R·T/100); three params; first `Float` math.
- **Comment:** Shipped as `SimpleInterestCalculatorV1` (Float version); the base
  `SimpleInterestCalculator` uses `Int` and isn't registered.

### 5. Profit & Loss Calculator  ·  `Ep2/ProfitLossCalculator`
- **Functionality:** 10 functions — `getSp1/getSp2`, `getCp1/getCp2`, `getPl1/2/3`, `getAbsPL1/2/3` (various `(cp/sp/pl/absPL)` combos)
- **Teaches:** deriving many quantities from related formulas; lots of parameter permutations.
- **Comment:** The most parameter-dense Ep2 app (10 semicolon groups in `paramNames`). Heavy but
  reinforces formula manipulation thoroughly.

### 6. Vote Age Checker  ·  `Ep2/VoteAgeChecker`
- **Functionality:** `canVote(age: Int): Boolean`
- **Teaches:** first **Boolean** return; first comparison/`if`.
- **Comment:** `paramNames="age"` is correct here.

### 7. Odd Even Checker  ·  `Ep2/OddEvenChecker`
- **Functionality:** `checkEvenOdd(num: Int): String`  (returns e.g. "Even"/"Odd")
- **Teaches:** modulo operator `%`; `if/else` returning a `String`.
- **Comment:** Returns a `String`, not a `Boolean` — the UI prints the word directly.

### 8. Max of two Nums  ·  `Ep2/MaxOfTwoNums`
- **Functionality:** `maxOf(x, y): Int`
- **Teaches:** `if/else` returning a value; comparison.

### 9. Leap Year Checker  ·  `Ep2/LeapYearChecker`
- **Functionality:** `isLeapYear(year: Int): Boolean`
- **Teaches:** compound boolean conditions (`%4`, `%100`, `%400`).
- **Comment:** ⚠️ `paramNames="age"` — mislabelled; the input is a **year**, not an age. Cosmetic
  (shows as the param name in starter code) but worth fixing.

### 10. Max of three Nums  ·  `Ep2/MaxOfThreeNums`
- **Functionality:** `maxOf(x, y, z): Int`
- **Teaches:** nested/chained conditionals; reusing the two-num idea.

### 11. Stage of Life  ·  `Ep2/StageOfLife`
- **Functionality:** `stageOfLife(age: Int): String`
- **Teaches:** `when` with ranges → returning different strings (child/teen/adult/…).

### 12. Grade Calculator  ·  `Ep2/GradeCalculator`
- **Functionality:** `getGrade(m1, m2, m3, m4, m5): String`
- **Teaches:** aggregate (average) then map to grade band with `when`.
- **Comment:** See also `MultiGradeCalculator.kt` (latest commit `c0ed903`) — a newer,
  not-yet-shipped variant.

---

## Episode 3 — Loops

### 13. Multiplication Table  ·  `Ep3/MultiplicationTable`
- **Functionality:** `printTable(num: Int): Unit`  (prints via `println`)
- **Teaches:** first **loop**; first `Unit`/side-effecting function; captured console output.
- **Comment:** Output is captured and shown (see `ExecutionHelper.runClass` stdout capture). The
  MiniApp screen special-cases names containing "Multipli" to disable parent scroll.

### 14. Greeting App  ·  `Ep3/GreetingApp`
- **Functionality:** `greet(name: String): String`
- **Teaches:** string templates/concatenation.

### 15. Multiplication Table V1  ·  `Ep3/MultiplicationTableV1`
- **Functionality:** `printTable(num, start, end): Unit`
- **Teaches:** loop with a configurable range (`start..end`).
- **Comment:** Natural follow-up to #13 — generalise the loop bounds.

### 16. Factor Calculator  ·  `Ep3/FactorCalculator`
- **Functionality:** `printFactorsOf(num): Unit`, `isPrime(num): Boolean`
- **Teaches:** loop + divisibility; deriving primality from factor count.
- **Comment:** `FactorCalculatorV1` (`Ep5`, `getFactorsOf -> List<Int>`) is the returns-a-list
  refactor (not shipped in the main list).

### 17. Factorial Calculator  ·  `Ep3/FactorialCalculator`
- **Functionality:** `factorialOf(num: Long): Long`, `permutationsOf(n, r): Long`, `combinationsOf(n, r): Long`
- **Teaches:** accumulating product in a loop; building nPr/nCr **on top of** factorial.
- **Comment:** Nice composition exercise (reuse `factorialOf`). Watch `Long` overflow for large n.

---

## Episode 3 — Patterns (printing 2D shapes)

Patterns are special: they **don't** go through the KSP processor. They share a handful of
hand-written functionality interfaces in `patterns/functionality/`, parameterised by a
`patternCode`. Factories live in `patterns/PatternMobileMiniApps.kt`; reference impls + tests
in `patterns/test/`.

Three interface shapes (`Pattern.Type`):
- **LinesBased** — `printPattern1(lines: Int)`
- **LinesAndCharBased** — `printPattern1(lines: Int, char: Char)`
- **LinesAndTwoCharsBased** — `printPattern1(lines, char1, char2)`

| # | App | Code | Type | repoPath |
|---|-----|------|------|----------|
| 18 | Pattern - BL | BL | LinesAndChar | `Ep3/Pattern_BL` |
| 19 | Pattern - TLN | TLN | LinesBased | `Ep3/Pattern_TLN` |
| 20 | Pattern - TRSR | TRSR | LinesBased | `Ep3/Pattern_TRSR` |
| 21 | Pattern - PU1 | PU1 | LinesAndChar | `Ep3/Pattern_PU1` |
| 22 | Pattern - PU2 | PU2 | LinesAndTwoChars | `Ep3/Pattern_PU2` |
| 23 | Pattern - PD3 | PD3 | LinesAndTwoChars | `Ep3/Pattern_PD3` |

- **Teaches:** nested loops; row/column reasoning; spaces vs. chars; building shapes
  (triangles, pyramids) of increasing difficulty.
- **Comment:** Output rendered by `PrintedPatternBox`. `supportsParentScroll=false` so the
  pattern grid lays out correctly. `Pattern_BR` exists (functionality/test) but isn't shipped.

---

## Episode 3.Ext — Advanced patterns & series

| # | App | Code | Type | repoPath |
|---|-----|------|------|----------|
| 24 | Pattern - P1 | P1 | LinesAndTwoChars | `Ep3.Ext/Pattern1` |
| 25 | Pattern - P2 | P2 | LinesAndTwoChars | `Ep3.Ext/Pattern2` |
| 26 | Pattern - P3 | P3 | LinesAndTwoChars | `Ep3.Ext/Pattern3` |
| 27 | Pattern - P4 | P4 | LinesAndTwoChars | `Ep3.Ext/Pattern4` |
| 28 | Pattern - P5 | P5 | LinesAndTwoChars | `Ep3.Ext/Pattern5` |

- **Comment:** ⚠️ In `PatternMobileMiniApps.kt`, **P3/P4/P5 all reuse `Pattern_P1_Test`** as
  their `testClass`. If the intended outputs differ, those will pass with P1's expectations —
  likely a copy-paste bug to verify against the reference impls.

### 29. Binomial Expansion  ·  `Ep3.Ext/BinomialExpansion`
- **Functionality:** `printBinomialExpansion(n: Int): Unit`
- **Teaches:** combining factorial/nCr with loops to print `(a+b)^n` coefficients (Pascal's row).

---

## Episode 4 — Harder loops, numbers & strings

### 30. Spiral  ·  `Ep4/Spiral`
- **Functionality:** `printFibonacciSeries(n: Int): Unit`  (`SpiralV1`/`Ep5`: `getFibonacciSeries -> List<Int>`)
- **Teaches:** generating a sequence (Fibonacci) with a loop / state carried across iterations.
- **Comment:** ⚠️ Name/behaviour mismatch — the app is called **"Spiral"** but the functionality
  prints a **Fibonacci series**. Confirm whether the rendered UI draws a spiral from the series
  or this is a leftover label. `supportsParentScroll=false`.

### 31. LCM HCF Calculator  ·  `Ep4/LCM-HCF`
- **Functionality:** `getLCM(x, y): Int`, `getHCF(x, y): Int`
- **Teaches:** Euclid's algorithm / loops; relationship `LCM·HCF = x·y`.
- **Comment:** `LcmHcfCalculatorV1` is a non-shipped variant.

### 32. Number Palindrome Checker  ·  `Ep4/NumberPalindrome`
- **Functionality:** `reverseNum(num: Long): Long`, `isPalindrome(num: Long): Boolean`
- **Teaches:** digit extraction with `%`/`/`; build reverse, then compare.

### 33. Armstrong Numbers  ·  `Ep4/ArmstrongNumbers`
- **Functionality:** `printArmstrongNums(upTo: Int): Unit`  (`ArmstrongNumbersV1`/`Ep5`: `getArmstrongNums -> List<Int>`)
- **Teaches:** digit powers + sum; nested loop (scan a range, test each).

### 34. String Palindrome Checker  ·  `Ep4/StringPalindrome`
- **Functionality:** `reverseStr(str): String`, `isPalindrome(str, ignoreCase): Boolean`
- **Teaches:** string indexing/iteration; reuse reverse for the check; an options flag.

### 35. Ceaser Cipher  ·  `Ep4/CeaserCipher`
- **Functionality:** `encode(text, delta, negative): String`, `decode(text, delta, negative): String`
- **Teaches:** char arithmetic / shifting within the alphabet; encode⇄decode symmetry.
- **Comment:** ("Ceaser" misspelling is baked into the type/file names — keep consistent if renaming.)

### 36. String Stats Calculator  ·  `Ep4/StringStatsCalculator`
- **Functionality:** 9 counters — `calcNoOfChars/Alphabets/Vowels/Consonants/Digits/SpecialChars/Spaces/…/Words(text)`
- **Teaches:** classifying chars with conditionals; counting in a loop; many small functions.

### 37. String Case Convertor  ·  `Ep4/StringCaseConvertor`
- **Functionality:** `convertToSentenceCase/TitleCase/Uppercase/Lowercase/SnakeCase(text): String`
- **Teaches:** string transformation, word boundaries, case mapping.

> The registry has 34 entries; counting V1-variants surfaced above as their own rows pushes the
> visible numbering to 37. The **shipped** apps are exactly the 34 in `MiniAppsHelper.getAll()`.

---

## Built but NOT in the shipped registry

Present in `faangX-KTP-Apps` source (authored / WIP / future episodes), but absent from
`MiniAppsHelper.getAll()`:

- **Episode 5 (functions, recursion, collections):**
  `ProgressionChecker` (+`V1`), `FactorCalculatorV1`, `ArmstrongNumbersV1`, `SpiralV1`,
  `LcmHcfCalculatorV1`, `MultiplicationTableV1` (this one *is* shipped) — the "V1" apps refactor
  Ep2–4 problems to **return collections** instead of printing.
- **IntListOps P1–P6** (`basics/intList/`) — a substantial collections track: each `IntListOp`
  (Add/Remove/Replace/Update/Find/Sort/Reverse/BinarySearch/ElementAccess/Mathematical) split
  across parts P1–P6 (P6 has A/B). Latest commits (`216d4e3`, `4f52270`, `c0ed903`) show this is
  the **active area of work**. Has full `*Test` + `*MobileMiniAppTest` coverage already.
- **`MultiGradeCalculator`** — newer grade variant (commit `c0ed903`).
- **Standalone apps** (no MiniApp registration / different track): `AtmSimulator`, `Base64App`,
  `CharFrequencyCalculator`, `NumberNameConvertor`, `NumberSystemConverter`,
  `PasswordStrengthChecker`, `RomanNumeralConvertor`, `SpreadsheetColNameConvertor`,
  `TowersOfHanoi` (the desktop `mainClass`).

---

## Quick review summary (things to fix when reviving the project)

1. **`LeapYearChecker` `paramNames="age"`** → should be `"year"`.
2. **Patterns P3/P4/P5 reuse `Pattern_P1_Test`** → verify each has its own expected output.
3. **"Spiral" app runs Fibonacci** → reconcile name vs. behaviour.
4. To ship an Ep5/IntListOps app, add its `Xxx_MobileMiniApp()` to `MiniAppsHelper.getAll()`
   and re-publish `com.faangx.ktp:mini-apps` (version bump in `apps/build.gradle.kts`).

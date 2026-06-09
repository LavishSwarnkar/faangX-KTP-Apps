# 07 — Study Material Index & MiniApp Roadmap

> **The study material came first.** The written course
> ([FaangX-KTP-Study-Material](https://github.com/The-Streamliners/FaangX-KTP-Study-Material),
> local: `…/KTP/FaangX-KTP-Study-Material/ktp`) was authored **by hand, end to end, before any
> MiniApp existed**. Each MiniApp was then designed *for a specific topic, in the same sequence
> the material teaches it*. So the study material — not the app registry — is the **curriculum
> source of truth**: it defines what must eventually be teachable, and the MiniApps are the
> interactive layer built on top of it, topic by topic.

This document indexes the entire written curriculum, marks which topics already have a MiniApp
(shipped or built), and proposes MiniApp ideas for the topics that don't yet have one.

- For *what a MiniApp is* and the UI↔functionality split, see [01-Architecture.md](01-Architecture.md).
- For the *catalogue of existing apps* (signatures, episodes, review notes), see [04-MiniApps-Index.md](04-MiniApps-Index.md).
- For the *daily-practice / exercise taxonomy* layered on apps, see [06-Daily-Practice-Pedagogy.md](06-Daily-Practice-Pedagogy.md).

**Coverage legend:** ✅ shipped (in `MiniAppsHelper.getAll()`) · 🟡 built but not shipped · 🔴 no MiniApp yet (idea below).

---

## The curriculum at a glance

The written material has two tiers and seven modules:

**Programming Fundamentals**
1. Programming Basics
2. Functional Programming
3. Object Oriented Programming
4. Collections

**Advanced Programming**
5. Algorithms
6. Using APIs
7. Asynchronous Programming

The existing ~34 shipped MiniApps cover **Module 1 thoroughly** (and a slice of Module 5).
Modules 2, 3, 6, 7 are almost entirely **un-apped**; Module 4 and the rest of Module 5 are
partially built but mostly **not shipped**. That gap is the roadmap.

---

## Module 1 — Programming Basics  ·  mostly ✅

Source: `programming-basics/` (function, data-types, variables, operators, comments, control-flow,
string, null, exceptions).

| Topic | Subtopics | Coverage | MiniApp(s) |
|-------|-----------|----------|------------|
| Function | definition, params, return, single-expression | ✅ | Text App, Perimeter/Area, Square, Simple Interest, Profit/Loss |
| Data Types | Int/Long/Float/Double, Char, Boolean, hex/bin literals | ✅ | Square (Long), Simple Interest (Float) |
| Variables | `val`/`var`, type inference | ✅ | (woven through all Ep2 apps) |
| Operators | arithmetic, `%`, comparison, logical, bitwise | ✅ | Odd/Even, Max-of-2/3, Vote Age |
| Control Flow → if/else, when | conditionals, `when` ranges | ✅ | Leap Year, Stage of Life, Grade Calculator |
| Control Flow → loops | `for`, `while`, `do-while`, `repeat`, `break`/`continue` | ✅ | Multiplication Table, Factor/Factorial, **all Patterns**, Armstrong, Spiral/Fibonacci |
| String | char codes, `isLetter/isDigit`, indexing, templates | ✅ | String Stats, String Case Convertor, String Palindrome, Caesar Cipher |
| Null | nullable types, `?.`, `?:`, `!!`, safe index | 🔴 | **idea below** |
| Exceptions | compile vs runtime, `try/catch/finally`, `throw`, custom | 🔴 | **idea below** |

### 🔴 Null Safety — "Null Defuser"
- **Functionality:** `safeCharAt(text: String, index: Int): Char?`, `firstUpper(text: String): Char?`,
  `parseAgeOrNull(input: String): Int?`, `displayName(nickname: String?, real: String?): String`
  (Elvis-chain fallback).
- **MiniApp idea:** a "form" UI where each field can be empty/garbage; the screen lights up green
  when a value is safely produced and shows a friendly fallback (never a crash) when it isn't. A
  live toggle flips between "naive `!!`" (which visibly *crashes* the preview with the actual
  exception) and "safe `?.`/`?:`" so students *see* the difference null-safety makes.
- **Teaches:** `?`, `?.`, `?:`, `?.let`, why `!!` is dangerous — by demonstration, not prose.

### 🔴 Exceptions — "Crash Lab"
- **Functionality:** `divide(a: Int, b: Int): String` (catch `ArithmeticException`),
  `parseAndDouble(s: String): String` (catch `NumberFormatException`),
  `getElement(list, i)` (catch `IndexOutOfBounds`), `validateAge(age): Int` (throw custom
  `InvalidAgeException`).
- **MiniApp idea:** a console-style screen that runs each function and renders the result as
  either a calm success card or a red "caught: <ExceptionType> — handled gracefully" card,
  with the raw stack trace tucked behind a "show details" expander. Students feel the
  try/catch contract: bad input becomes a handled message, not a dead app.
- **Teaches:** exception types, `try/catch/finally`, `throw`, custom exceptions.

---

## Module 2 — Functional Programming  ·  🔴 (almost entirely un-apped)

Source: `functional-programming.md` — lambdas (function types, lambda expressions, `it`,
trailing lambda), higher-order functions (function as arg / as return, `::` references),
imperative vs declarative (the PhotoRenaming case study).

Coverage: only `ProgressionChecker`/`V1` (the `generateSeries` exercise) exists, and it's 🟡 not shipped.

### 🔴 "Lambda Forge" — build & run function values
- **Functionality:** student implements named *vals* of function type:
  `val double: (Int)->Int`, `val shout: (String)->String`, `val isEven: (Int)->Boolean`,
  `val clamp: (Int,Int,Int)->Int`.
- **MiniApp idea:** a board of "function chips". Tap a chip, feed it an input via a slider/field,
  watch the output animate out. The point is the *value-ness* of functions — each chip is a `val`
  you can pass around. A second row lets you **compose** two chips (`f then g`) and see the
  pipeline output, making composition tangible.
- **Teaches:** function types, lambda expressions, `it`, functions as first-class values.

### 🔴 "Series Studio" (productionised ProgressionChecker)  ·  🟡→ship
- **Functionality:** `generateSeries(firstTerm: Int, noOfTerms: Int, getNextTerm: (Int)->Int): List<Int>`.
- **MiniApp idea:** student passes a *rule lambda* (`it+1`, `it*2`, `2*it+5`, `it/2`) and the
  screen plots the resulting sequence as an animated line/bar chart. Switch the lambda → the
  curve re-draws live. This is the canonical higher-order-function exercise, upgraded from a
  printed list to a visual generator.
- **Teaches:** function-as-argument, trailing lambda, why HOFs are powerful.

### 🔴 "Pipeline" — declarative vs imperative (PhotoRenaming, gamified)
- **Functionality:** the declarative chain from the material —
  `map`/`groupBy`/`mapValues`/`flatten`/`toMap` — exposed as small student-filled steps that
  rename photos by `<city><index>.<ext>`.
- **MiniApp idea:** a visual data-pipeline where each stage is a card showing the *shape* of the
  data flowing through (`List<String>` → `List<Photo>` → `Map<City,List<Photo>>` → …). The
  student fills one transform per stage and watches sample data morph through the pipe. A toggle
  shows the equivalent imperative loop count ("you replaced 40 lines of loops with 6 operators").
- **Teaches:** function composition, declarative thinking, the STL HOF toolkit. Capstone of Module 2.

---

## Module 3 — Object Oriented Programming  ·  🔴 (entirely un-apped)

Source: `object-oriented-programming/` — class & object, inheritance, data class, companion object,
singleton, interface, abstract class, enum class, sealed class.

This is the **biggest opportunity**: rich, real-world examples already exist in the material
(Students Grade, Income Tax, Bank Account hierarchy, 2D Shapes, QuizState). The MiniApp pattern
needs a small twist: the student implements **methods of a class** (or an interface impl) rather
than a free function — the UI drives an *object*, not just a function.

### 🔴 "Bank Simulator" — Class & Object + Inheritance  (uses the material's Bank Account example)
- **Functionality:** a `BankAccount` with `deposit(amount)`, `withdraw(amount)`, `getBalance()`,
  `printStatement()`; then `SavingsAccount` / `CurrentAccount` / `LoanAccount` overriding interest
  & overdraft rules.
- **MiniApp idea:** an actual mini-banking screen — balance card, deposit/withdraw buttons,
  scrolling statement. Swap the account *type* with a chip and the same UI now obeys different
  rules (savings adds interest, current allows overdraft, loan accrues). One UI, three subclasses —
  inheritance you can operate.
- **Teaches:** encapsulation, constructors, methods, `open`/`override`, `super`.

### 🔴 "Shape Studio" — Interface vs Abstract class  (uses the 2D Shapes example)
- **Functionality:** `interface Shape { fun area(): Double; fun perimeter(): Double }` implemented
  by `Circle`, `Rectangle`, `Triangle`; then an `abstract class Shape2D` variant to contrast.
- **MiniApp idea:** student picks a shape, the screen *draws it to scale* on a Compose canvas and
  shows live area/perimeter as they drag handles to resize. Adding a new shape = implementing the
  interface; the gallery picks it up automatically — the exact "common structure" argument the
  material makes for interfaces.
- **Teaches:** interfaces, abstract classes, polymorphism, when to use which.

### 🔴 "Quiz State Machine" — Sealed class  (uses the QuizState example)
- **Functionality:** `sealed class QuizState { Loading; Question(...); Result(score); Error(msg) }`
  + a `reduce(state, event): QuizState` transition function.
- **MiniApp idea:** a real one-screen quiz that *is* its state — the UI is an exhaustive `when`
  over the sealed type, so each state renders a different card. Students feel why sealed classes +
  exhaustive `when` beat enums/booleans for modelling app state (no `else` branch needed; compiler
  enforces completeness). This doubles as a primer for Compose state used later in the course.
- **Teaches:** sealed classes, exhaustive `when`, state modelling.

### 🔴 "Enum Toolbox" — Enum class  (Weekday / Planet / Direction)
- **Functionality:** an `enum class` with properties and member functions, e.g.
  `Planet(mass, radius) { fun surfaceGravity(): Double; fun weightOf(earthKg): Double }`.
- **MiniApp idea:** a dial/wheel of enum values; selecting one shows its computed properties
  (your weight on each planet, next/previous weekday, opposite compass direction). `Enum.values()`
  drives the wheel automatically.
- **Teaches:** enums with data, member functions, `values()`/`valueOf()`, `ordinal`.

### 🔴 "Object Factory" — data class + companion object + singleton
- **Functionality:** `data class Photo(...)` with `companion object { fun fromCsv(line): Photo }`
  (factory), plus a `singleton object IdGenerator { fun next(): Int }`.
- **MiniApp idea:** paste CSV-ish lines → factory parses them into cards; `copy()` a card with one
  field changed and watch `equals()`/`toString()` update for free; the singleton stamps each card
  with a guaranteed-unique id (prove there's only one counter). Shows *why* data classes and
  factories reduce boilerplate.
- **Teaches:** `data class` (`equals`/`hashCode`/`copy`/`toString`), companion factory functions, singleton/`object`.

---

## Module 4 — Collections  ·  🟡 (built, mostly not shipped)

Source: `collections/` — Arrays, ArrayLists, Sets, Map (+ Pair), and advanced ops:
`all`/`any`, `count`, safe `get`, `reverse`, `filter`, `transform`(map), `reduce`,
`associate`/`associateBy`/`associateWith`, `groupBy`.

Coverage: **IntListOps P1–P6** (Add/Remove/Replace/Update/Find/Sort/Reverse/BinarySearch/
ElementAccess/Mathematical) is built with full tests but 🟡 **not shipped** — shipping it covers
Arrays/ArrayLists. The functional ops (filter/map/reduce/associate/group) and Sets/Maps still lack
dedicated apps.

### 🟡→ship: IntListOps P1–P6
Already authored (`basics/intList/`). Ship by adding each `*_MobileMiniApp()` to
`MiniAppsHelper.getAll()` and bumping `mini-apps`. Covers create/access/modify/search/sort/reverse.

### 🔴 "Collection Lab" — the functional toolkit (filter/map/reduce/associate/group)
- **Functionality:** over a `List<Person(name, age, city)>`:
  `adults(): List<Person>` (filter), `names(): List<String>` (map),
  `totalAge(): Int` (reduce/sum), `byCity(): Map<String,List<Person>>` (groupBy),
  `nameToAge(): Map<String,Int>` (associate).
- **MiniApp idea:** a live data-table where each operator is a toggle/stage; flip one and the table
  re-renders the transformed result with a visible row-count delta. The same dataset reshapes
  itself as you chain operators — the declarative toolkit made visceral, and a natural bridge from
  the PhotoRenaming pipeline in Module 2.
- **Teaches:** `filter`, `map`, `reduce`/`fold`, `groupBy`, `associate*`, lambda predicates/selectors.

### 🔴 "Set Theory Playground" — Sets
- **Functionality:** `union(a,b)`, `intersect(a,b)`, `subtract(a,b)`, `dedupe(list): Set`.
- **MiniApp idea:** two editable tag-clouds (set A, set B) rendered as an interactive **Venn
  diagram**; the result of the chosen set operation highlights live as you add/remove elements.
  Shows uniqueness + unordered nature concretely.
- **Teaches:** `Set`, set operations, dedup semantics.

### 🔴 "Word Frequency / Inverted Index" — Map mastery
- **Functionality:** `wordCount(text): Map<String,Int>`, `topN(text, n): List<Pair<String,Int>>`,
  `charHistogram(text): Map<Char,Int>`.
- **MiniApp idea:** paste any paragraph → an animated **bar-chart word cloud** of frequencies,
  top-N leaderboard, and a char histogram. Modern, genuinely useful (the basis of search/analytics),
  and exercises Map building + `getOrPut`/`groupingBy().eachCount()`.
- **Teaches:** `Map`, `Pair`, accumulation patterns, `groupingBy`, sorting entries.

---

## Module 5 — Algorithms  ·  partial

Source: `algorithms/` — general (swap), char (case-conversion, equality, parse-digit),
string (case, equality, parsing-numbers, replace, reverse, search, substring, trim),
sorting (bubble, insertion, selection — material; quick/heap mentioned in `programs.md`),
list (associate, filter, reduce, reverse, search, transform).

Coverage: string/char algorithms are effectively covered by **String Stats / Case Convertor /
Palindrome / Caesar** (✅). Binary search lives in IntListOps (🟡). **Sorting visualisation has no
app.**

### 🔴 "Sorting Visualizer" — Bubble / Insertion / Selection (+ Quick/Heap)
- **Functionality:** `bubbleSort(list): List<Int>`, `insertionSort(...)`, `selectionSort(...)`
  — but instrumented: each returns/streams the sequence of swaps so the UI can animate.
  (Variant: `sortSteps(list): List<Step>`.)
- **MiniApp idea:** the classic **animated bars** — student's algorithm drives the animation;
  bars swap in real time, comparison/swap counters tick, and a speed slider lets you watch
  insertion vs selection diverge. The single most motivating algorithms app; turns an abstract
  loop into something you watch *work*. Pairs perfectly with daily-practice "trace" exercises.
- **Teaches:** nested loops, in-place swaps, comparison logic, algorithm cost intuition.

### 🔴 "Search Detective" — Linear vs Binary search
- **Functionality:** `linearSearch(list, target): Int`, `binarySearch(sortedList, target): Int`
  returning index (and step count).
- **MiniApp idea:** a sorted row of cards; tap "find X" and watch linear crawl left-to-right vs
  binary halving the range — side by side, with a step counter proving O(n) vs O(log n).
- **Teaches:** binary search, why sortedness matters, complexity by demonstration.

### 🔴 "String Toolkit" — the string-algorithm pack as one app
- **Functionality:** reimplement-the-stdlib set: `myReverse`, `myTrim`, `mySubstring(s,a,b)`,
  `myReplace(s,old,new)`, `myIndexOf(s,sub)`, `myEquals(a,b,ignoreCase)`.
- **MiniApp idea:** a text field plus a panel of operations; each shows the student's result beside
  the real `String.x()` output with a ✓/✗ match badge — implement the STL yourself and verify
  against the real thing live. (Consolidates the scattered string algos into one motivating app.)
- **Teaches:** string indexing, char-by-char algorithms, building stdlib from scratch.

---

## Module 6 — Using APIs  ·  🔴 (entirely un-apped)

Source: `using-apis/` — File I/O (read/write/append, PrintWriter, Scanner, serialization),
Connecting to the Internet (HTTP GET/POST, status codes, `URL.readText()`).

These need network/file capability in the on-device runtime — confirm the sandbox allows it before
committing. If network is allowed, these are high-impact "wow" apps.

### 🔴 "Fact Fetcher" — HTTP GET  (uses the material's numbersapi.com example)
- **Functionality:** `fetchMathFact(num: Int): String` (`URL("http://numbersapi.com/$num/math").readText()`),
  `fetchAndParse(url): String`.
- **MiniApp idea:** a number dial → tap to fetch a live math fact, rendered in a card with a
  loading state. First time the student's code *touches the internet* — hugely motivating. Natural
  lead-in to the async module (same example reused there for parallelism).
- **Teaches:** APIs as reusable interfaces, HTTP GET, request/response, parsing text.

### 🔴 "Pastebin Poster" — HTTP POST
- **Functionality:** `postSnippet(text: String): String` returning the created paste URL.
- **MiniApp idea:** type a note → "publish" → get a shareable link back. Teaches POST bodies,
  status codes, and the GET/POST distinction the material covers.
- **Teaches:** HTTP POST, request headers/body, status codes.

### 🔴 "Notes on Disk" — File I/O + serialization
- **Functionality:** `save(notes: List<String>)`, `load(): List<String>`,
  `serialize(note): String` / `deserialize(line): Note`.
- **MiniApp idea:** a tiny notes app whose persistence layer the student writes; add a note, kill
  & reopen the screen, notes survive — because *their* save/load works. (Sandbox file path
  permitting.)
- **Teaches:** files, read/write/append, (de)serialization to text.

---

## Module 7 — Asynchronous Programming  ·  🔴 (entirely un-apped)

Source: `asynchronous-programming/` — intro (sync vs async, threads), basics (`CoroutineScope`,
`launch`, nesting), examples (even/odd, multiples, network), `suspend` + `delay`, Job
(`join`/`joinAll`/`cancel`/status), async/await (`Deferred`/`awaitAll`), scope builders
(`coroutineScope`/`GlobalScope`/`supervisorScope`/`runBlocking`), exception handling
(`CoroutineExceptionHandler`, `supervisorScope`).

The richest "show, don't tell" module — coroutines are *inherently* about timing, which animation
captures perfectly.

### 🔴 "Parallel vs Sequential" — the headline coroutines demo  (uses the material's exact example)
- **Functionality:** `runSequential(n): Long` and `runParallel(n): Long` — fire N simulated
  tasks (or the numbersapi fetches) and return elapsed millis; the parallel version uses
  `launch`/`coroutineScope`.
- **MiniApp idea:** two lanes of progress bars racing — sequential fills one-by-one, parallel fills
  together — with a live stopwatch on each proving parallel is ~Nx faster. This single screen makes
  the entire *case for* coroutines, mirroring the 2210ms→540ms result in the material.
- **Teaches:** `launch`, `coroutineScope`, structured concurrency, why async matters.

### 🔴 "Loading Animator" — `suspend` + `delay`  (uses the Loading Animation example)
- **Functionality:** `suspend fun spinner(): String` / `suspend fun progress(onTick)` driving an
  animated loader via `delay()`.
- **MiniApp idea:** student's suspend function powers an actual spinner/progress ring; changing the
  `delay` values changes the animation speed live — `delay` as a non-blocking pause you can see.
- **Teaches:** `suspend`, `delay`, non-blocking vs `Thread.sleep`.

### 🔴 "Job Control Panel" — Job lifecycle  (join / cancel / status)
- **Functionality:** launch a long task and expose `start()`, `cancel()`, `isActive/isCompleted/isCancelled`.
- **MiniApp idea:** a download-manager-style card with Start/Cancel buttons and a live status badge
  (Active → Completed, or Cancelled). Students *operate* a Job and watch cooperative cancellation
  (`isActive` checks) actually stop work mid-flight.
- **Teaches:** `Job`, `join`/`joinAll`, `cancel`, cancellation cooperation, status flags.

### 🔴 "Async Dashboard" — `async`/`await` + error isolation  (supervisorScope)
- **Functionality:** `loadDashboard(): Dashboard` that `async`-launches several independent fetches
  and `awaitAll()`s them; a supervisor variant so one failing tile doesn't sink the rest.
- **MiniApp idea:** a dashboard of tiles (weather/score/fact) that each resolve independently; flip
  a "make tile 2 fail" switch and watch plain-scope (whole dashboard dies) vs `supervisorScope`
  (only tile 2 shows an error). Makes `CoroutineExceptionHandler` and supervision concrete.
- **Teaches:** `async`/`await`/`awaitAll`, `Deferred`, `supervisorScope`, exception handling.

---

## Roadmap summary — priority order

The study material's own sequence suggests this build order (each block is a coherent "episode set"):

1. **Finish Module 1** — ship 🔴 *Null Defuser* + *Crash Lab* (closes the only basics gaps).
2. **Ship Module 4 base** — promote 🟡 *IntListOps P1–P6* to the registry (already authored & tested).
3. **Module 2 (Functional)** — *Lambda Forge* → *Series Studio* (ship ProgressionChecker) → *Pipeline*. High value, currently a big hole.
4. **Module 3 (OOP)** — *Bank Simulator*, *Shape Studio*, *Quiz State Machine*, *Enum Toolbox*, *Object Factory*. Largest gap; needs the "implement-a-class" MiniApp variant.
5. **Module 4 (Functional collections)** — *Collection Lab*, *Set Playground*, *Word Frequency*.
6. **Module 5 (Algorithms)** — *Sorting Visualizer*, *Search Detective*, *String Toolkit*. Most visually rewarding.
7. **Module 6 (APIs)** — *Fact Fetcher*, *Pastebin Poster*, *Notes on Disk* — **gated on runtime network/file permissions**; verify the on-device sandbox first.
8. **Module 7 (Async)** — *Parallel vs Sequential* → *Loading Animator* → *Job Control Panel* → *Async Dashboard*. Coroutines are tailor-made for animated MiniApps.

> **Design constraint for every idea above:** keep the MiniApp pattern intact — the student writes
> **pure functions / class methods** (the "functionality"), and a prebuilt, polished Compose UI
> renders the result. None of these are plain input→output boxes; each turns the function the
> student writes into something that *visibly does work* (animates, draws, races, persists, or
> fetches). For OOP/async apps, the functionality contract extends from free functions to
> class methods / `suspend` functions — a modest generalisation of the existing KSP pattern
> (see [02-KSP-CodeGeneration.md](02-KSP-CodeGeneration.md)).

# 01 — Architecture & End-to-End Flow

This document explains how every piece fits together to deliver a MiniApp to a student.

---

## 1. The core idea: separate UI from "functionality"

Every MiniApp is a `@Composable` function whose only inputs are **plain Kotlin function
references** — the "functionality". The UI knows *how to render and react*; it does not know
*how to compute*. Computing is the student's job.

```kotlin
// faangX-KTP-Apps/.../basics/SquareOfNumApp.kt
@MiniApp(
    name = "Square Of a Number",
    repoPath = "ProgrammingFundamentals/Ep2/SquareOfNum",
    paramNames = "num"
)
@Composable
fun SquareOfNumAppV1(
    getSquareOf: (Long) -> Long      // ← the "functionality" the student must implement
) {
    var num by remember { mutableStateOf("") }
    val square = derivedStateOf { num.toLongOrNull()?.run(getSquareOf) ?: SMILE_EMOJI }
    // ... OutlinedTextField + HighlightedText showing the square ...
}
```

The student never sees this Compose code at first. They only see:

```kotlin
fun getSquareOf(num: Long): Long {
    TODO()
}
```

and a live app whose answer box updates the moment their function is correct.

---

## 2. What KSP generates (the glue)

A function reference like `(Long) -> Long` can't be compiled, tested, serialized, or
injected on its own. So the **`ksp` module** turns every `@MiniApp`-annotated composable
into a small ecosystem of types. For `SquareOfNumAppV1` it generates (see
[02-KSP-CodeGeneration.md](02-KSP-CodeGeneration.md) for details):

| Generated symbol | Purpose |
|---|---|
| `interface SquareOfNumAppV1Functionality { fun getSquareOf1(num: Long): Long }` | The **contract**. A nameable type the app can cast to and inject. (`1` suffix avoids clashing with the student's own `getSquareOf`.) |
| `class SquareOfNumAppV1FunctionalityImpl : …Functionality` | Delegates `getSquareOf1(num)` → the top-level `getSquareOf(num)` the **student writes**. This is the class the Android app instantiates after compiling student code. |
| `fun SquareOfNumAppV1(functionality: …Functionality)` | Overload that adapts the interface back into the function-reference UI (`getSquareOf = functionality::getSquareOf1`). |
| `fun SquareOfNumV1_MobileMiniApp(): MobileMiniApp<…>` | **Factory + metadata bundle** (label, repoPath, the composable, the reference test impl, the test class, and **string snapshots** of the interface/impl/funs). |
| `…_Interface_AsString()`, `…_Impl_AsString()`, `…_Funs_AsString()` | The above as **plain strings**, so the Android editor can show the starter code and assemble a compilable file without shipping source. |
| `<Name>Test.functionality()` (from `@MiniAppTest`) | Adapts the instructor's reference test object so the same JUnit test runs against either the reference impl or the student's impl. |

> Note: a few apps (the **Patterns**) keep this generated-style scaffolding **checked in by
> hand** under `patterns/functionality/` rather than regenerating it, because several pattern
> MiniApps share one functionality interface parameterised by a `patternCode`.

---

## 3. `MobileMiniApp<T>` — the unit of currency

[`MobileMiniApp.kt`](../apps/src/main/kotlin/com/faangx/ktp/MobileMiniApp.kt) is the single
object that travels from the library into both consumer apps. It carries **everything**
needed to display, edit, compile, inject and test one MiniApp:

```kotlin
class MobileMiniApp<T>(
    val label: String,                 // "Square Of a Number"
    val functionalityClass: Class<T>,  // SquareOfNumAppV1Functionality::class.java  → for casting
    val functionalityInterface: String,// source text of the interface (for the editor)
    val functionalityImpl: String,     // source text of the Impl class
    val functionalityFuns: String,     // starter `fun … { TODO() }` shown to the student
    val functionalityImplClassName: String, // "SquareOfNumAppV1FunctionalityImpl"
    val testFunctionality: T,          // instructor's REFERENCE implementation (for "Run Sample")
    val testClass: Class<*>?,          // the *MobileMiniAppTest JUnit class to run
    val packageName: String,
    val repoPath: String,              // path inside the student's KTP-Course git repo
    val supportsParentScroll: Boolean = true,
    val composable: @Composable (T) -> Unit  // renders the UI given a functionality instance
)
```

`simpleName()` (label with spaces removed, `&`→`n`) is the stable key used everywhere
(registry lookup, `MiniAppFunctionalityHelper`, test wiring).

---

## 4. The functionality registry (runtime injection point)

[`MiniAppFunctionalityHelper`](../apps/src/main/kotlin/com/faangx/ktp/MiniAppFunctionalityHelper.kt)
is a tiny global `Map<String, Any>`. This is the seam between **compiled-at-runtime student
code** and the **prebuilt UI**:

1. Android app compiles student code → loads `…FunctionalityImpl` → `saveFunctionality(simpleName, impl)`.
2. The MiniApp screen calls `getFunctionality(simpleName)` and passes it to `miniApp.Composable(...)`.
3. The generated `*MobileMiniAppTest` also pulls from this map so JUnit tests the student's impl.

---

## 5. End-to-end flow (student app)

```
ListScreen ─tap─▶ EditorScreen
   │                  │ shows miniApp.functionalityFuns (or saved file) in Sora editor
   │                  │
   │             [Compile/Run] ─▶ EditorViewModel.compileAndBuild
   │                  │              1. assemble file: package + interface + student funs + Impl
   │                  │                 (MobileMiniApp.mainFileContent)
   │                  │              2. ExecutionHelper.compile()  → embedded kotlinc → .jar → D8 → classes.dex
   │                  │              3. getFunctionality(implFQN)  → MultipleDexClassLoader → newInstance()
   │                  │              4. MiniAppFunctionalityHelper.saveFunctionality(simpleName, impl)
   │                  ▼
   │             MiniAppScreen ─▶ miniApp.Composable( getFunctionality(simpleName) )   // live app!
   │
   │             [Test] ─▶ EditorViewModel.test
   │                          compile (as above) → MiniAppTester.test(miniApp.testClass)
   │                          → JUnit Platform runs *MobileMiniAppTest against student impl
   │                          → on pass: mark Solved, commit & push to student's GitHub fork
   │
   └─ [Run Sample] ─▶ MiniAppScreen with miniApp.testFunctionality (instructor reference impl)
```

See [03-Android-App-Flow.md](03-Android-App-Flow.md) for the compiler internals.

---

## 6. Build & publish chain

```
ksp module ──(:ksp)──▶ apps module ──maven-publish──▶ com.faangx.ktp:mini-apps:<version>
                                                          (JitPack / local Maven)
                                                                │
                          ┌─────────────────────────────────────┼────────────────────────┐
                          ▼                                     ▼                          ▼
                 KTP-MiniApps-App (Android)        MiniApps-Catalog (Desktop)     (any consumer)
                 libs.faangx.ktp.apps              implementation(...mini-apps)
```

- **`apps/build.gradle.kts`** applies `org.jetbrains.compose`, KSP, and `maven-publish`
  (artifact `com.faangx.ktp:mini-apps`, version e.g. `1.58-local`). It depends on
  `project(":ksp")` both as `implementation` and `ksp(...)`, and pulls JUnit Jupiter so the
  generated `*MobileMiniAppTest` classes ship inside the artifact.
- **`ksp/build.gradle.kts`** uses KSP API + KotlinPoet (code emission) + ktfmt (formatting).
- Toolchain: **JDK 17**, Kotlin **1.9.21**, Compose **1.5.11**, KSP **1.9.20-1.0.14**.
- The Android app excludes the `*-desktop` Compose material artifacts (it substitutes the
  Android ones) and the `ktfmt`/`javax.inject` transitive deps — see its `configurations { }`
  block. It re-implements ktfmt locally under `core/ktfmt/`.

---

## 7. The Catalog app (instructor tool)

`faangX-KTP-MiniApps-Catalog` is a small Compose-for-Desktop app (`Main.kt`,
`MiniAppsCatalog.kt`, `MiniAppsMenu.kt`) that renders every MiniApp using **reference
implementations** under `catalog/demo/*Demo.kt`. It's for the instructor to eyeball all apps
at once while authoring; it is **not** part of the student loop. (Its own `MiniApp` enum /
`MiniAppsAggregator` provide category grouping for the menu.)

---

## 8. Map of `faangX-KTP-Apps` source

| Path | What's there |
|---|---|
| `apps/.../MobileMiniApp.kt`, `MiniAppComp.kt`, `MiniAppFunctionalityHelper.kt` | Core types: the bundle, the desktop `MiniApp(...)` window wrapper, the runtime registry. |
| `apps/.../basics/*.kt` | Most MiniApp UIs (numbers, conditionals, strings). One file may hold several apps + `V1` variants. |
| `apps/.../basics/intList/` | `IntListOps` MiniApps — modelled as a list of `IntListOp` operations split into parts P1–P6. |
| `apps/.../patterns/` | Pattern MiniApps: `Pattern` model, shared `functionality/` interfaces, `test/` reference impls + tests, `single/` & `comp/` UI, `PatternMobileMiniApps.kt` factories. |
| `apps/.../comp/` | Reusable Compose components (`DynamicRowColumn`, `HighlightedText`, …). |
| `apps/.../num_pad/`, `led/` | Extra UI building blocks (numeric pad, LED-grid display) for richer apps. |
| `apps/.../test/*Test.kt` | Instructor **reference** test objects (`@MiniAppTest`) + `testcases()`. |
| `apps/.../test/mobile/*MobileMiniAppTest.kt` | Generated/standalone JUnit classes the Android app actually executes. |
| `ksp/.../FunctionalityProcessor.kt` | The code generator. |
| `ksp/.../MiniApp.kt`, `MiniAppTest.kt` | The annotations. |

> **Heads-up for "getting this running again":** the registry that defines *which* apps ship
> and *in what order* lives in the **Android app**, not here:
> `KTP-MiniApps-App/.../catalog/MiniAppsHelper.kt`. Adding a new MiniApp means (1) write the
> `@MiniApp` composable here, (2) write its `@MiniAppTest` + `*MobileMiniAppTest`, (3) bump &
> publish `mini-apps`, (4) add `Xxx_MobileMiniApp()` to `MiniAppsHelper.getAll()`.

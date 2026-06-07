# 02 — KSP Code Generation

The `ksp` module is a [KSP](https://kotlinlang.org/docs/ksp-overview.html) symbol processor
that, for every `@MiniApp`-annotated composable, generates the entire "functionality"
ecosystem described in [01-Architecture.md](01-Architecture.md). It uses **KotlinPoet** to
emit code and **ktfmt** to format it.

Files:
- [`MiniApp.kt`](../ksp/src/main/kotlin/ksp/MiniApp.kt) / [`MiniAppTest.kt`](../ksp/src/main/kotlin/ksp/MiniAppTest.kt) — the annotations
- [`FunctionalityProcessor.kt`](../ksp/src/main/kotlin/ksp/FunctionalityProcessor.kt) — the generator
- [`FunctionalityProcessorProvider.kt`](../ksp/src/main/kotlin/ksp/FunctionalityProcessorProvider.kt) — registered via `resources/META-INF/services/...SymbolProcessorProvider`
- `KtfmtFormatter.kt` — wraps ktfmt

---

## The annotations

```kotlin
@Target(AnnotationTarget.FUNCTION) @Retention(SOURCE)
annotation class MiniApp(
    val name: String,        // human label → MobileMiniApp.label  e.g. "Square Of a Number"
    val repoPath: String,    // path in the KTP-Course git repo     e.g. "…/Ep2/SquareOfNum"
    val paramNames: String = "",        // see "paramNames grammar" below
    val supportsParentScroll: Boolean = true
)

@Target(AnnotationTarget.CLASS) @Retention(SOURCE)
annotation class MiniAppTest      // marks an instructor reference-test object
```

### `paramNames` grammar
The annotated composable's parameters are **function types** (e.g. `(Long) -> Long`,
`(String, Int, Boolean) -> String`). KSP only knows them as `p0, p1, …`. `paramNames` gives
them friendly names that show up in the generated interface/impl:

- Commas separate the **arguments of one** functionality function: `"x, y, z"`.
- Semicolons separate **multiple** functionality functions on the same app:
  `"cp, pl; sp, pl"` → first fun takes `(cp, pl)`, second takes `(sp, pl)`.

Example — `ProfitLossCalculator` has 10 function-type params, hence 10 semicolon groups.

---

## What `processFunction()` emits (per app)

For composable `Foo` in package `p`, all written into one generated file `p/FooFunctionality.kt`:

| Builder method | Output |
|---|---|
| `generateInterface` | `interface FooFunctionality { fun <param>1(<named args>): <ret> … }` — one abstract fun per function-type parameter, suffixed `1`. |
| `generateTopLevelFunctions` | The **starter code** string: `fun <param>(<named args>): <ret> { TODO() }` — what the student edits. (Built but only used as a string.) |
| `generateImplClass` | `class FooFunctionalityImpl : FooFunctionality { override fun <param>1(args) = <param>(args) }` — bridges interface→student's top-level fun. |
| `generateTestImplExtFun` | `fun FooTest.functionality(): FooFunctionality` — adapts the `@MiniAppTest` object's lambdas into the interface. |
| `generateComposableFunction` | `@Composable fun Foo(functionality: FooFunctionality) = Foo(<param> = functionality::<param>1, …)` — interface→function-reference adapter. |
| `generateDemoFunction` | `fun FooDemo() { … }` — a desktop preview entry point using the reference impl. |
| `generateMiniAppComposableFunction` | `fun <Foo without App>MiniApp(...)` — wraps the composable in the desktop `MiniApp(title=…)` window. |
| `generateMobileMiniAppFunction` | `fun <Foo>_MobileMiniApp(): MobileMiniApp<FooFunctionality>` — the metadata bundle factory (the most important output). |
| `generateStringRepresentation` ×3 | `fooFunctionality_Interface_AsString()`, `_Impl_AsString()`, `_Funs_AsString()` — the interface/impl/starter code as **runtime strings** for the editor. |

### Post-processing
`writeAll()` formats with ktfmt and strips `public ` and `: Unit` to keep generated/displayed
code idiomatic and concise.

### Naming conventions (important & easy to trip on)
- Interface: `<FunctionName>Functionality`
- Impl class: `<FunctionName>FunctionalityImpl`
- Factory fun: `<FunctionName **with "App" removed**>_MobileMiniApp()`
  → `SquareOfNumAppV1` ⟶ `SquareOfNumV1_MobileMiniApp`, `TextApp` ⟶ `Text_MobileMiniApp`.
- Test class referenced: `<FunctionName w/o "App">MobileMiniAppTest` and `<… w/o "App">Test`.
- Interface methods get a `1` suffix so the student's own `getSquareOf` never collides with
  the interface's `getSquareOf1`.

---

## `@MiniAppTest` two-phase processing

`@MiniAppTest` objects are processed in `finish()` (deferred a round) because they may
reference types generated in the first round. For object `FooTest` with a
`test(functionality: FooFunctionality, testcase: …)` method, `generateTestExtensionFunction`
emits a `FooTest.test(...)` extension that takes the individual functions as lambdas, builds
an anonymous `FooFunctionality`, and calls the real `test`. This lets the **same** test body
run against the reference impl (catalog/desktop) and the student's compiled impl (Android).

---

## Worked example: `SquareOfNumAppV1`

Input:
```kotlin
@MiniApp(name="Square Of a Number", repoPath="…/Ep2/SquareOfNum", paramNames="num")
@Composable fun SquareOfNumAppV1(getSquareOf: (Long) -> Long) { … }
```
Generated (abridged):
```kotlin
interface SquareOfNumAppV1Functionality { fun getSquareOf1(num: Long): Long }

class SquareOfNumAppV1FunctionalityImpl : SquareOfNumAppV1Functionality {
    override fun getSquareOf1(num: Long): Long = getSquareOf(num)   // calls student's fun
}

@Composable fun SquareOfNumAppV1(functionality: SquareOfNumAppV1Functionality) =
    SquareOfNumAppV1(getSquareOf = functionality::getSquareOf1)

fun SquareOfNumV1_MobileMiniApp(): MobileMiniApp<SquareOfNumAppV1Functionality> =
    MobileMiniApp(
        label = "Square Of a Number",
        functionalityClass = SquareOfNumAppV1Functionality::class.java,
        functionalityInterface = squareOfNumAppV1Functionality_Interface_AsString(),
        functionalityImpl      = squareOfNumAppV1Functionality_Impl_AsString(),
        functionalityFuns      = squareOfNumAppV1Functionality_Funs_AsString(),  // "fun getSquareOf(num: Long): Long { TODO() }"
        functionalityImplClassName = "SquareOfNumAppV1FunctionalityImpl",
        testFunctionality = SquareOfNumV1Test.functionality(),
        testClass = SquareOfNumV1MobileMiniAppTest::class.java,
        packageName = "com.faangx.ktp.basics",
        repoPath = "ProgrammingFundamentals/Ep2/SquareOfNum",
        supportsParentScroll = true,
        composable = { SquareOfNumAppV1(it) }
    )
```

On the phone the editor shows only `functionalityFuns`; the app concatenates
`package + interface + <student funs> + impl` (`MobileMiniApp.mainFileContent`), compiles it,
instantiates `SquareOfNumAppV1FunctionalityImpl`, and injects it.

---

## Gotchas / maintenance notes

- **Function-type params only.** The processor assumes every parameter of the annotated
  composable is a function type; it reads arg types via `KSType.arguments` and names them
  from `paramNames`. A non-functional parameter would break generation.
- **`paramNames` count must match.** Too few names → `IndexOutOfBounds` while mapping; missing
  group → falls back to a single `"param"`. Keep commas/semicolons in sync with the signature.
- **Nullability** of functional params was a real bug fixed in commit `5afd6af`
  ("KSP FunctionalityProcessor - nullability info for functional params").
- **`"App"` stripping** in the factory name is purely textual — naming a composable `…App`
  changes its generated `_MobileMiniApp` name. Patterns avoid the processor entirely.
- The `classExists()` helper logs warnings for missing referenced classes — handy when a
  `*MobileMiniAppTest` or `*Test` hasn't been written yet.

# 03 — Student Android App Flow (`KTP-MiniApps-App`)

The student app is where the magic of "write a function, see a real app" happens. Its
headline trick: **it embeds a full Kotlin compiler and compiles the student's code on the
phone**, then injects the result into the prebuilt MiniApp UI from the `mini-apps` artifact.

> Package root: `com.faangx.miniApps`. Min SDK 26, target/compile SDK 34, JDK 17, multidex,
> core-library desugaring on (needed for the JVM toolchain libs).

---

## Key dependencies that make it possible

From `app/build.gradle.kts`:

| Lib (`libs.*`) | Role |
|---|---|
| `faangx.ktp.apps` | The `com.faangx.ktp:mini-apps` artifact — all MiniApp UIs + generated `*_MobileMiniApp()` factories + `*MobileMiniAppTest` classes. |
| `kotlinc` | Embedded Kotlin compiler (compile student source → `.jar`). |
| `r8` | D8 — dexes the compiled `.jar` → `classes.dex`. |
| `smali.dexlib2` | Reads the produced DEX (class discovery). |
| `nb.javac.android`, `guava` | javac/runtime deps the embedded compiler needs on Android. |
| `sora.editor` (+ `editor-textmate` module) | The code editor UI with Kotlin syntax highlighting. |
| `junit` + `junit.platform.launcher` + `jupiter.*` | Run the MiniApp's JUnit tests on-device. |
| `kgit` (JGit wrapper) + `snakeyaml` | Clone/commit/push the student's course repo; edit `task-info.yaml`. |
| `generativeai` (Gemini) | "Get AI Help" on compile errors. |
| `koin.android` | DI. `gson`, `datastore` | persistence. `markdownview` | render AI/markdown. |

The app **excludes** the `*-desktop` Compose material artifacts that `mini-apps` pulls in and
substitutes the Android equivalents — this is the "not KMP" substitution that lets the same
Compose UI code render on Android.

---

## On-device compilation pipeline

```
Student code (Sora editor)
   │  EditorViewModel.save / compileAndBuild
   ▼
MobileMiniApp.mainFileContent(funs):
   "package <pkg>\n <interface>\n <student funs>\n <impl class>"
   │
   ▼  ExecutionHelper.compile()                (core/compiler/main/ExecutionHelper.kt)
   │     Compiler(context, project, buildReporter).compile()
   │       1. KotlinCompiler → .jar      (against extracted kotlin-stdlib jars, see SetupCompiler)
   │       2. JavaCompileTask / JarTask  (core/compiler/internal/jar/)
   │       3. D8Task → classes.dex       (core/compiler/internal/D8Task.kt)
   │     BuildReporter streams OUTPUT/ERROR; "Build completed successfully." resumes the coroutine
   │
   ▼  ExecutionHelper.getFunctionality(implFQN)
   │     MultipleDexClassLoader.loadDex(classes.dex)
   │     loader.loadClass("<pkg>.<…>FunctionalityImpl").newInstance()
   │
   ▼  MiniAppFunctionalityHelper.saveFunctionality(miniApp.simpleName(), impl)
   │
   ▼  MiniAppScreen → miniApp.Composable( getFunctionality(simpleName) )   // the live app
```

- **First-run setup** (`SetupCompiler.setupCompiler()`, called from `KTPMiniAppsApp` /
  `MainActivity`): extracts `kotlin-stdlib-1.9.0.jar`, `kotlin-stdlib-common-1.9.0.jar`,
  `core-lambda-stubs.jar` from `assets/jars/` to the classpath dir, disables javac modules,
  and creates the working `Project` (`ProjectHelper.createProject()`).
- **Error line mapping:** because student funs are spliced into a larger file, compiler error
  line numbers are shifted. `ExecutionHelper.adjustErrorMessages` + `ProjectHelper.getLineAdjustment`
  translate them back to the student's editor coordinates.
- **`MultipleDexClassLoader`** loads the freshly built DEX alongside the app's own classloader
  so the student's `…FunctionalityImpl` can implement an interface that lives in the
  `mini-apps` artifact already on the classpath. (DEX is made read-only via
  `makeDexReadOnlyIfNeeded` to satisfy Android's loader.)

---

## Testing the student's solution

`EditorViewModel.test()`:
1. Compile + load + `saveFunctionality(...)` (same as above).
2. `MiniAppTester.test(miniApp.testClass)` runs the JUnit-Platform `*MobileMiniAppTest` for
   that app (`catalog/MiniAppTester.kt` uses `LauncherDiscoveryRequest` +
   `SummaryGeneratingListener`).
3. The test pulls the student's impl out of `MiniAppFunctionalityHelper` (see
   `SquareOfNumV1MobileMiniAppTest`) and asserts against parameterised test cases.
4. Result dialog: all-pass → "Congratulations"; failures list per-case messages.
5. On pass, `saveExecutionInfo` records solved status locally and, if git is set up, updates
   `task-info.yaml` and **commits + pushes** to the student's fork.

---

## Git / course-repo integration

The student's exercises live in a GitHub repo **`<username>/KTP-Course`**, a fork of
`The-Streamliners/KTP-Course` (`GitRepoCloneHelper`: forks via GitHub API, then `KGit.clone`).

- A MiniApp's `repoPath` (e.g. `ProgrammingFundamentals/Ep2/SquareOfNum`) maps to a folder in
  that repo containing `src/Task.kt` and `task-info.yaml`.
- `KTPCourseRepo.getCode(path)` reads `Task.kt`, strips imports/`fun main()`, and shows just
  the functions in the editor; `updateCode` merges the student's edits back (`mergeCode`
  preserves imports + `fun main()`); `updateTaskInfoStatus` writes `status: Solved` + feedback
  on success.
- When git is **not** initialised, the app falls back to a local `miniAppsDir/<simpleName>.kt`
  scratch file (`ProjectHelper.miniAppFile`).

Relevant files: `feature/git/*`, `helper/git/*`, `domain/model/KTPCourseRepo.kt`,
`data/LocalRepo.kt`, `data/MiniAppRepo.kt`.

---

## Screen map (Compose Navigation — `ui/KTPMiniAppsNavHost.kt`, `ui/Screen.kt`)

| Screen | File | Role |
|---|---|---|
| List | `feature/list/ListScreen.kt` + `comp/MiniAppCard.kt` | Renders `MiniAppsHelper.getAll()`; tap → editor, "Run Sample" → live app with reference impl. |
| Editor | `feature/editor/EditorScreen.kt` + `EditorViewModel.kt` | Sora code editor; Save / Compile&Run / Test / AI-help. |
| MiniApp | `feature/miniApp/MiniAppScreen.kt` | Hosts the live `miniApp.Composable(functionality)`; `sample=true` uses `testFunctionality`. |
| MiniApp Info | `feature/miniAppInfo/*` | Per-app metadata. |
| Git | `feature/git/GitScreen.kt` + `GitViewModel.kt` | Credentials, clone/fork, commit/push management. |
| Execution Test | `feature/ExecutionTestScreen.kt` | Dev harness for the compiler. |

> The registry `catalog/MiniAppsHelper.kt` defines the **shipped set and order** — it's the
> source of truth for [04-MiniApps-Index.md](04-MiniApps-Index.md).

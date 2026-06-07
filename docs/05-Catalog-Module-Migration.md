# 05 — Catalog Module Migration

The MiniApps **Catalog** (instructor-facing Compose-for-Desktop gallery) was previously a
standalone repo (`faangX-KTP-MiniApps-Catalog`) that pulled the MiniApps from JitPack
(`com.github.LavishSwarnkar:faangX-KTP-Apps:1.12`). It is now a **module in this repo**
(`:catalog`) that consumes the MiniApps locally via a project dependency.

## How to run

```bash
./gradlew :catalog:run
```

A `1200×700` desktop window titled **"Mini Apps Catalog"** opens with a category menu on the
left and the selected MiniApp (driven by a reference implementation) on the right.

## What was done

1. **New module** `catalog/` added to `settings.gradle.kts` (`include("catalog")`).
2. **Sources copied** from the old repo into `catalog/src/` (the old repo is left intact and is
   now superseded). The stray `src/main/kotlin/Main.kt` (an `org.example` "Hello World" that
   didn't match the configured `mainClass`) was dropped — the real entry point is the
   top-level `main()` in `MiniAppsCatalog.kt`.
3. **`catalog/build.gradle.kts`** created: Kotlin/JVM + `org.jetbrains.compose`, Java 17
   toolchain, `compose.desktop.currentOs`, `material3-desktop:1.6.0`, and crucially
   **`implementation(project(":apps"))`** instead of the JitPack coordinate.
   `mainClass = "com.faangx.ktp.catalog.MiniAppsCatalogKt"`.

### Dependency choice: `project(":apps")`, not m2local
Because the Catalog now lives in the same Gradle build as `:apps`, a direct **project
dependency** is the cleanest option: no `publishToMavenLocal` step, no version bumps, and the
Catalog always builds against the current `:apps` source (including freshly KSP-generated
code). Using `mavenLocal()` would have required republishing `mini-apps` on every change — the
project dependency avoids that entirely.

## Build fixes required (and why)

Wiring `project(":apps")` exposed three issues — two were build-infra, one was real API drift
(the old JitPack `1.12` artifact predates a lot of the current `:apps` code).

1. **Compose plugin clash across modules.** With `:catalog` added, *two* modules now apply
   `org.jetbrains.compose`, which made Gradle build the plugin's shared build service under two
   classloaders and fail with:
   `Shared build service 'ConfigurationProblemReporterService' parameters have unexpected type`.
   **Fix:** declare the plugins once on the root classpath with `apply false` in the root
   `build.gradle.kts`:
   ```kotlin
   plugins {
       kotlin("jvm") apply false
       id("org.jetbrains.compose") apply false
   }
   ```

2. **`:ksp` had no Java toolchain.** `:apps` and `:catalog` target Java 17, but `:ksp`
   compiled with whatever JDK ran the Gradle daemon (Java 21 here), producing an incompatible
   variant: *"No matching variant of project :ksp … declares a component compatible with Java
   21 and the consumer needed Java 17."*
   **Fix:** pin `:ksp` to the same toolchain in `ksp/build.gradle.kts`:
   ```kotlin
   java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }
   ```

3. **Demo files referenced the old `:apps` API.** The copied `catalog/demo/*Demo.kt` were
   written against `mini-apps 1.12`:
   - **Moved packages:** `com.faangx.ktp.basics.string.{Base64App, CeaserCipher,
     PasswordStrengthChecker, PasswordStrengthCheckResult}` and
     `com.faangx.ktp.basics.loops.{Spiral, SpiralConfig}` are now flattened under
     `com.faangx.ktp.basics`. Fixed the imports for the still-used demos
     (Base64 / CeaserCipher / PasswordStrengthChecker) and the `PatternsApp` import
     (`patterns.catalog` → `basics`).
   - **Dead files deleted:** `PatternAppDemo.kt` and `SpiralAppDemo.kt` — not referenced by the
     `MiniApp` enum or menu, and they referenced removed APIs (`PatternMiniApp`, `SpiralConfig`).
   - **Top-level name clash:** `Base64AppDemo.kt` and `CeaserCipherDemo.kt` both declared
     `encode`/`decode` in the same package. Base64's variants were unused (its composable uses
     `encodeV1`/`decodeV1`), so they were renamed to `encodeV2`/`decodeV2`.
   - **API signature drift:** `:apps` `CeaserCipher` gained a `negative: Boolean` parameter
     (`encode/decode: (String, Int, Boolean) -> String`). Updated the demo's `encode`/`decode`
     to accept and honour it (`negative` flips the shift direction).

## Verification

- `./gradlew :catalog:compileKotlin` — ✅
- `./gradlew :catalog:compileTestKotlin` — ✅
- `./gradlew :catalog:run` — ✅ launches `MiniAppsCatalogKt.main`, BUILD SUCCESSFUL (the app
  window starts and the JVM runs without throwing).

## Distribution safety (JitPack) — solutions are not exposed

The Catalog's `demo/*Demo.kt` files contain **reference solutions**, so they must never reach
the published `mini-apps` artifact. Two structural guarantees + one hardening ensure this:

1. **`:catalog` has no `maven-publish`** → JitPack publishes no catalog artifact (only `:apps`
   and `:ksp` apply `maven-publish`).
2. **Dependency direction is `:catalog → :apps`** (never the reverse; `:apps` depends only on
   `:ksp`). So the `mini-apps` artifact and its POM contain zero catalog code — the Android app
   resolving `com.github.LavishSwarnkar:faangX-KTP-Apps` can't transitively reach the solutions.
3. **Hardening — excluded from the JitPack build entirely.** `settings.gradle.kts` only includes
   `:catalog` when `-PexcludeCatalog` is *not* set, and `jitpack.yml` runs
   `./gradlew publishToMavenLocal -PexcludeCatalog`. So on JitPack the catalog isn't even
   compiled. Verified via `./gradlew projects` (catalog present by default, absent with the
   flag) and `publishToMavenLocal -PexcludeCatalog --dry-run` (only `:apps`/`:ksp` publish).

Local development is unaffected — `./gradlew :catalog:run` works as before (the flag defaults
to off).

## Follow-ups / notes

- The pre-existing **"ksp 1.9.20 is too old for kotlin 1.9.21"** warning is unrelated and
  harmless (it predates this change); resolve later by aligning KSP/Kotlin versions.
- `catalog/demo/` still carries dead helper functions (`encode1`, `encode2`, `pattern2..13`,
  etc.) inherited from the old repo — left as-is to minimise churn.
- The old standalone `faangX-KTP-MiniApps-Catalog` repo can now be archived/retired.

# FaangX-KTP — System Documentation

> FaangX is a company that trains students in Software Engineering using Kotlin.
> **KTP** (Kotlin Training Program) is its flagship course.
> **MiniApps** are the core teaching innovation: single-screen Compose apps where the
> *functionality* is extracted out as plain Kotlin functions. Students first see only the
> function signatures and must write the function bodies — no UI jargon — and instantly
> see their code drive a real, polished app on Desktop and Android.

This `docs/` folder documents the **entire system** across its three codebases.

---

## The Three Codebases

| Repo | Role | This folder lives in |
|------|------|----------------------|
| **[faangX-KTP-Apps](https://github.com/LavishSwarnkar/faangX-KTP-Apps)** | **The heart.** Every MiniApp's Compose UI + the KSP module that generates the "functionality" scaffolding (interface, impl, tests, factory) for each app. Published as the Maven artifact `com.faangx.ktp:mini-apps`. | ✅ You are here |
| **[faangX-KTP-MiniApps-Catalog](https://github.com/LavishSwarnkar/faangX-KTP-MiniApps-Catalog)** | A Compose-for-Desktop gallery to preview every MiniApp at once (used by the instructor for demos / authoring). | — |
| **[KTP-MiniApps-App](https://github.com/LavishSwarnkar/KTP-MiniApps-App)** | The **student Android app**. Lists all MiniApps, gives each a code editor, **compiles the student's Kotlin on-device**, injects it into the real MiniApp UI, and runs JUnit tests against it. Syncs progress to the student's GitHub fork of the course. | — |

> **"Not KMP, but some other technique"** — the MiniApp UIs are written once as
> Compose-for-Desktop (JVM) code in `faangX-KTP-Apps`. The Android app depends on that same
> JVM artifact and renders it directly (Android Compose and Desktop Compose share the same
> `androidx.compose` APIs at the source level). The student's code is compiled **at runtime
> on the phone** with an embedded Kotlin compiler and wired into the prebuilt UI via an
> interface. See [03-Android-App-Flow.md](03-Android-App-Flow.md).

---

## Document Index

1. **[01-Architecture.md](01-Architecture.md)** — How every piece fits together end-to-end.
   The "functionality interface" pattern, the data flow, the build/publish chain.
2. **[02-KSP-CodeGeneration.md](02-KSP-CodeGeneration.md)** — Deep dive on the `ksp` module:
   the `@MiniApp` / `@MiniAppTest` annotations and exactly what code the
   `FunctionalityProcessor` generates for each app.
3. **[03-Android-App-Flow.md](03-Android-App-Flow.md)** — The student app: on-device Kotlin
   compilation → DEX → classloading → injection → JUnit testing → Git sync.
4. **[04-MiniApps-Index.md](04-MiniApps-Index.md)** — Catalogue of **every MiniApp**: its
   function signature, what concept it teaches, the course episode it maps to, and review
   comments.
5. **[05-Catalog-Module-Migration.md](05-Catalog-Module-Migration.md)** — How the instructor
   Catalog became the local `:catalog` module (`./gradlew :catalog:run`) consuming `:apps`
   directly instead of JitPack, and the build fixes that required.
6. **[06-Daily-Practice-Pedagogy.md](06-Daily-Practice-Pedagogy.md)** — Turning MiniApps into a
   Duolingo-style daily-practice app for **algorithm design**: an 11-type exercise taxonomy
   *derived automatically* from each app's reference solution + `@MiniAppTest`, the streak/practice
   mechanics, and a repeatable authoring recipe for existing and future MiniApps.

---

## The 30-second mental model

```
                       faangX-KTP-Apps (JVM library)
   ┌──────────────────────────────────────────────────────────────────┐
   │  @MiniApp                                                          │
   │  @Composable fun SquareOfNumAppV1(getSquareOf: (Long)->Long) {...} │  ← UI you write once
   │                          │                                         │
   │                          ▼  KSP (FunctionalityProcessor)           │
   │  • interface SquareOfNumAppV1Functionality { fun getSquareOf1(..) }│  ← contract
   │  • ...FunctionalityImpl  (delegates to a top-level `getSquareOf`)  │  ← what student fills
   │  • SquareOfNumV1_MobileMiniApp(): MobileMiniApp<...>               │  ← factory / metadata
   │  • string snapshots of all the above (for the editor)             │
   └──────────────────────────────────────────────────────────────────┘
                          │ published as com.faangx.ktp:mini-apps
                          ▼
        ┌─────────────────────────────┐        ┌──────────────────────────┐
        │  KTP-MiniApps-App (Android) │        │ MiniApps-Catalog (Desktop)│
        │  student writes the body of │        │ instructor previews all   │
        │  `getSquareOf`, app compiles│        │ apps with reference impls │
        │  it on-device, injects impl │        └──────────────────────────┘
        │  into the prebuilt UI, runs │
        │  JUnit tests, pushes to Git │
        └─────────────────────────────┘
```

# AGENTS.md — Working on faangX-KTP-Apps

Context and rules for AI agents (and humans) working in this repository.

## What this project is

**FaangX** trains students in Software Engineering with Kotlin; **KTP** (Kotlin Training
Program) is its course. **MiniApps** are single-screen Compose apps whose core *functionality*
is extracted as plain Kotlin functions — students write only the function bodies (no UI jargon)
and see their code drive a real app on Desktop and Android.

This repo (`faangX-KTP-Apps`) is the **heart**: it holds every MiniApp's Compose UI, the KSP
module that generates the per-app "functionality" scaffolding, the instructor Catalog, and is
published as the Maven artifact `com.faangx.ktp:mini-apps`. Two sibling repos consume it: the
student Android app (`KTP-MiniApps-App`) and — historically — the standalone catalog (now the
`:catalog` module here).

## Documentation — read before changing things

All long-form docs live in [`docs/`](docs/):

- [docs/README.md](docs/README.md) — master index, the MiniApp concept, the three codebases, a 30-second mental model.
- [docs/01-Architecture.md](docs/01-Architecture.md) — end-to-end flow; the UI↔functionality split; `MobileMiniApp<T>`; build/publish chain; source map.
- [docs/02-KSP-CodeGeneration.md](docs/02-KSP-CodeGeneration.md) — the `:ksp` processor; `@MiniApp`/`@MiniAppTest`; the `paramNames` grammar; what gets generated per app.
- [docs/03-Android-App-Flow.md](docs/03-Android-App-Flow.md) — the student app: on-device Kotlin compile → DEX → classload → inject → JUnit → Git sync.
- [docs/04-MiniApps-Index.md](docs/04-MiniApps-Index.md) — catalogue of every MiniApp: signature, concept taught, course episode, review comments.
- [docs/05-Catalog-Module-Migration.md](docs/05-Catalog-Module-Migration.md) — how the Catalog became the local `:catalog` module and the build fixes it needed.

## Modules

| Module | Role |
|--------|------|
| `:ksp` | KSP `FunctionalityProcessor` + `@MiniApp`/`@MiniAppTest` annotations. Generates the interface/impl/factory/test glue for each MiniApp. |
| `:apps` | All MiniApp Compose UIs + reference tests. Published as `com.faangx.ktp:mini-apps`. Depends on `:ksp`. |
| `:catalog` | Instructor Compose-for-Desktop gallery. Depends on `:apps`. **Contains reference SOLUTIONS** in `catalog/demo/` — never published, excluded from JitPack. |

## Common commands

```bash
./gradlew :catalog:run                       # launch the MiniApps Catalog (desktop)
./gradlew :apps:test                         # run MiniApp reference tests
./gradlew publishToMavenLocal                # publish :apps + :ksp to ~/.m2
./gradlew publishToMavenLocal -PexcludeCatalog   # what JitPack runs (no catalog)
```

- Toolchain: **JDK 17** (all modules pinned), Kotlin **1.9.21**, Compose **1.5.11**, KSP **1.9.20-1.0.14**.
- The registry of *which* MiniApps ship and in what order lives in the **Android app**
  (`KTP-MiniApps-App/.../catalog/MiniAppsHelper.kt`), not here.

## Gotchas

- The Compose plugin must stay declared with `apply false` in the **root** `build.gradle.kts`
  so multiple modules can apply it without the build-service classloader clash.
- `:catalog` is gated behind `-PexcludeCatalog` in `settings.gradle.kts`; keep it that way so
  CI/JitPack never compiles or publishes the solutions.
- Pre-existing harmless warning: `ksp 1.9.20 is too old for kotlin 1.9.21`.
- `.idea/` is untracked and git-ignored — do not re-add it.

## RULE: changelog on "wrap up"

When the user says **"wrap up"** (or otherwise ends a working session), you MUST, before
finishing:

1. **Append to `changelog/{today}.md`** (create it if missing; `{today}` is the current date in
   `YYYY-MM-DD`). Record what changed this session: a short summary, the concrete edits
   (files/modules), notable decisions, and any follow-ups. Convert relative dates to absolute.
2. **Update `changelog/index.md`** — add/refresh the one-line entry pointing to that day's file
   (newest first), e.g. `- [2026-06-07](2026-06-07.md) — <one-line summary>`.

Keep entries factual and skimmable. One file per day; multiple sessions in a day append to the
same dated file under separate `##` headings.

# 06 — Daily Practice & Exercise Design

> How to turn the MiniApps platform into a **Duolingo-style daily-practice app** for learning
> **algorithm design** — and a repeatable recipe for minting *abundant* exercises from every
> MiniApp, present and future.

---

## 1. The thesis

Programming is **converting ideas into instructions**. The hard, transferable skill is not Kotlin
syntax — it's **algorithm design**: decomposing a problem into a correct, ordered sequence of
steps. A MiniApp is the *boss level* of that skill: "here's the idea (a working app) and the
function signature — write the steps that make it real."

But a single "implement this function" task is a **one-shot** exercise. Duolingo's whole engine
runs on the opposite: **many small, varied, repeatable reps** that drill one skill from many
angles, every day, forever. To get there we don't need new MiniApps — we need to **decompose each
MiniApp into a family of micro-exercises** and wrap them in daily-goal / streak / practice
mechanics.

The good news: **every MiniApp already ships the two raw materials** needed to generate that
family automatically.

| Asset (already exists) | Where it lives | What it seeds |
|---|---|---|
| **Reference solution** | `catalog/.../demo/<App>Demo.kt` (the instructor's correct impl) | Parsons, fill-in-the-blank, predict-output, spot-the-bug, pseudocode |
| **Testcases + oracle** | `apps/.../test/<App>Test.kt` (`@MiniAppTest`: `testcases()` + `test()`) | input→output pairs, the validator for implement-from-scratch, edge-case hunts |
| **Signature + `paramNames`** | `@MiniApp(...)` on the Compose fn | the prompt skeleton, the IO shape |
| **Concept tags** | catalog `category` / `subcategory`, docs `EpN` | the skill tree (units & lessons) |

> **Design principle:** an exercise type is *authored once* as a transformation over these assets,
> then it **auto-instantiates for every MiniApp** that supplies the assets. Abundance is a
> by-product, not per-app labour.

---

## 2. The exercise taxonomy

Eleven exercise types, ordered roughly from "read instructions" → "design instructions". Each row
notes how it's generated and which skill of algorithm design it drills.

### A. Reading & tracing (build the mental model)

| # | Type | Prompt | Generated from | Drills |
|---|---|---|---|---|
| 1 | **Predict the Output** | Given the code + an input, type/choose the output. | reference solution run on one `testcases()` input | tracing execution, building a runtime mental model |
| 2 | **Dry-run the Loop** | Fill a trace table: variable values after each iteration. | reference solution + an input (loop apps) | understanding loop state, the heart of iterative algorithms |
| 3 | **Match Input → Output** | Pair inputs with their outputs (or pick the odd one out). | several `testcases()` IO pairs | inferring behaviour from examples |

### B. Manipulating instructions (the algorithm-design core)

| # | Type | Prompt | Generated from | Drills |
|---|---|---|---|---|
| 4 | **Order the Steps (Parsons)** | Shuffled lines of the solution — drag into correct order. | reference solution, lines shuffled (+ optional distractor lines) | **pure step-ordering with zero syntax burden** — the single most on-target exercise |
| 5 | **Fill in the Blank (cloze)** | Solution with a token/line/operator masked — supply it. | reference solution, mask one node of the AST | precise recall of the *one* step that matters |
| 6 | **Spot / Fix the Bug** | A subtly wrong version — find the line, then fix it. | reference solution, one mutation (`<`→`<=`, `+`→`-`, off-by-one, swapped branch) | debugging = reasoning backwards from wrong output to wrong step |
| 7 | **Problem → Pseudocode** | Given the idea, arrange/choose plain-English steps (no code). | hand-authored canonical pseudocode (Parsons over English) | **idea → instructions**, before syntax exists |
| 8 | **Pseudocode → Code** | Translate given pseudocode into Kotlin. | pseudocode + reference solution as oracle | instructions → language; bridges 7 and 9 |

### C. Authoring & hardening (mastery)

| # | Type | Prompt | Generated from | Drills |
|---|---|---|---|---|
| 9 | **Implement from Scratch** | The classic MiniApp: write the body, see the app run, pass tests. | signature + `@MiniAppTest` validator | the whole skill, end-to-end (the boss level) |
| 10 | **Edge-case Hunt** | Find an input where *this* (plausible-but-wrong) impl fails. | a mutant + the oracle to confirm divergence | adversarial thinking; this is literally what `testcases()` authoring is |
| 11 | **Refactor / Simplify** | Given a correct-but-verbose impl, shorten it (must still pass). | a "naive" variant + the oracle | recognising that many step-sequences solve one problem |

**Why this ordering matters pedagogically.** A learner meeting a new concept walks *down* the
table: first they trace it (A), then they manipulate given steps (B), then they author from
nothing (C). Parsons (4) and Problem→Pseudocode (7) are the keystones — they isolate
"order the steps" from "remember the syntax," which is exactly the idea→instructions skill.

---

## 3. Where abundance comes from

One MiniApp → **dozens** of distinct exercise instances, because each type is *parameterised*:

- **Predict-output / Match**: one item **per testcase**. `CeaserCipherTest.testcases()` already
  yields 80 cases (10 phrases × 4 deltas × 2) → 80 predict-output items for free.
- **Parsons**: re-shuffle each session; an N-line solution has many wrong orders to rule out.
- **Fill-in-the-blank**: one item **per maskable node** (each operator, bound, literal, branch).
- **Spot-the-bug / Edge-case**: one item **per mutation operator × per site** (off-by-one,
  comparator flip, swapped branch, wrong init…).
- **Multi-function apps multiply**: `IntListOperations` exposes ~25 functions (access, math, find,
  update, add, remove, replace, reverse, binary search, sort) — each is its own exercise family.
  `ProfitLossCalculator` has 10 formula functions. These are abundance goldmines.

> Rule of thumb: **abundance = (exercise types) × (testcases) × (maskable/mutable sites) ×
> (functions per app)**. You author the *types* once; the multipliers come from assets you already
> wrote.

---

## 4. Daily-practice mechanics (the Duolingo layer)

Map the game loop onto the assets above.

### Skill tree (units → lessons)
Reuse the existing taxonomy — no new modelling needed:

```
Unit (category)              Lesson group (subcategory)     Lesson (MiniApp)
─────────────────────────────────────────────────────────────────────────────
Simple one-line Functions    —                              Square of Number, Simple Interest…
Control Flow                 if-else                        Vote Age, Odd/Even, Max of Two…
                             if-else-if / when              Max of Three, Stage of Life, Grade…
                             for loop                       Multiplication Table, Factorial…
                             while loop                     Number System Converter, LCM/HCF
Strings                      —                              Palindrome, Caesar Cipher, Base64…
Collections                  —                              Int List Operations (×25 ops)…
```
(Backed by `MiniApp.category`/`subcategory` and the docs `EpN` ordering. The `EpN` order is the
unlock order.)

### Core loop
- **Daily goal:** complete *N* exercises (default 10–15), **mixed types**, **mixed concepts** —
  drawn mostly from *already-unlocked* lessons, plus a little new material.
- **Streak:** consecutive days the goal is met. Add a **streak freeze** and a lightweight
  "weekend/streak-saver" so a missed day doesn't nuke months of work (Duolingo's retention trick).
- **XP / hearts:** XP per correct rep; optional hearts (lose one on a wrong answer in a lesson,
  refill via practice) to make accuracy matter without being punitive.

### Practice section (the review engine) — **spaced repetition over concepts**
This is the part that makes daily practice *infinite*:

- Track **mastery per (concept, exercise-type)**, decaying over time (SM-2 / FSRS-style).
- The practice queue surfaces **weakest + most-overdue** items first — e.g. a learner who keeps
  flipping `<` vs `<=` gets more *Spot-the-bug on loop bounds*; someone shaky on `when`-ranges gets
  more *Stage-of-Life predict-output*.
- "Practice" never runs dry because items are *generated*, not a finite hand-written bank.
- Mastery is shown as a per-concept bar in the tree (crack/refresh like Duolingo's "legendary").

### Lesson structure (within one MiniApp)
Walk the taxonomy as difficulty ramps:
```
Trace (predict-output, dry-run)  →  Manipulate (Parsons, fill-blank, fix-bug)
   →  Translate (pseudocode↔code)  →  Author (implement from scratch)  →  Harden (edge-case, refactor)
```
A learner "completes" a MiniApp by clearing implement-from-scratch; the earlier rungs are the
scaffolding that makes that boss level reachable — and the later rungs feed the practice queue.

---

## 5. The authoring recipe (for existing & future MiniApps)

To make a MiniApp **practice-ready**, supply an *Exercise Spec* alongside it. Most fields are
already present or derivable; only a few need a human.

```
Exercise Spec  (per MiniApp / per function)
├─ idea            : one plain-English sentence — the problem ("classify a number as odd or even")
├─ signature       : from @MiniApp fn + paramNames                       [exists]
├─ referenceSolution: the demo impl                                       [exists in catalog/demo]
├─ oracle+testcases: @MiniAppTest test()/testcases()                      [exists in apps/test]
├─ concept tags    : category/subcategory + EpN                           [exists]
├─ difficulty      : 1–5 (drives unlock order & queue weighting)          [author: 1 number]
├─ pseudocode      : canonical step list, plain English                   [author: ~5 lines]   ← enables #4,#7,#8
├─ bugVariants?    : optional curated mutations worth surfacing            [author: optional]   ← else auto-mutate
├─ distractors?    : optional wrong Parsons lines / MCQ options            [author: optional]
└─ hints/explain?  : optional per-step explanation shown after a miss      [author: optional]
```

Everything in `[exists]` is free. The only **required new** authoring is `difficulty`, `idea`, and
`pseudocode`. With those, exercise types **1–6 and 9–11 auto-generate**; `7–8` need the
`pseudocode` field (which you wrote anyway).

### Checklist for a **new** MiniApp
1. Build the MiniApp UI + `@MiniApp` signature + `paramNames` (as today).
2. Write the **reference solution** as the catalog demo (as today — see [05](05-Catalog-Module-Migration.md)).
3. Write **`@MiniAppTest`** `testcases()` + `test()` oracle (as today — see [02](02-KSP-CodeGeneration.md)).
4. **New:** add the Exercise Spec fields — `idea`, `difficulty`, `pseudocode` (3 small things).
5. Tag concept(s) (reuse category/subcategory).
6. *Optional polish:* curated bug variants, MCQ distractors, per-step hints.

Result: the app slots into the tree and immediately contributes a *family* of daily exercises.

> Keep steps 2 & 3 honest: the reference solution and the oracle are now **load-bearing for the
> whole exercise engine**, not just the catalog. A weak `testcases()` weakens predict-output,
> edge-case, and the implement-from-scratch validator all at once.

---

## 6. Worked examples (existing apps)

**Odd/Even Checker — `checkEvenOdd(num): String`** *(Control Flow ▸ if-else, Ep2)*
- *Idea:* "decide whether a number is even or odd."
- *Pseudocode:* `if num % 2 == 0 → "Even" else → "Odd"`.
- Generates: predict-output per input (…, -3, 0, 7, …); fill-blank on `% 2` and on the `== 0`
  comparator; spot-the-bug (`% 2 == 1`, or swapped branches); Parsons over the 3 lines; the classic
  implement task. **~30 reps from one 3-line function.**

**Multiplication Table — `printTable(num): Unit`** *(Control Flow ▸ for loop, Ep3)*
- *Idea:* "print the 1×–10× table for a number."
- The **dry-run table** shines here: fill `i` and `num*i` for each of 10 iterations.
- Parsons over `for`/`println` lines; fill-blank on the range `1..10`; bug = `1..9` (off-by-one) or
  `num + i`. Output is captured (stdout) and compared by the oracle.

**Caesar Cipher — `encode/decode(text, delta, negative)`** *(Strings, Ep4)*
- `testcases()` already yields **80** cases → 80 predict-output / match items immediately.
- Edge-case hunt is natural: find a char where a naive impl (no wraparound, or shifting
  punctuation) diverges — exactly what `shiftChar`'s `% 26` guards against.
- High-value spot-the-bug bank: wraparound, case boundary, non-letter passthrough.

**Int List Operations — ~25 ops in one app** *(Collections)*
- Each op (`findMax`, `binarySearch`, `removeAll`, `replaceAll`, in-place vs new-list…) is its own
  exercise family. In-place-vs-new-list pairs make superb **compare/contrast** and
  **spot-the-bug** material (mutating vs returning). One app ≈ a whole unit's worth of practice.

---

## 7. Rollout sketch (non-binding)

1. **Pick two generators first:** *Predict-output* (read) and *Parsons* (design). Highest
   value-per-effort, both fully auto from assets that already exist — proves the abundance thesis
   with zero new per-app content.
2. Add the **Exercise Spec** (`idea`/`difficulty`/`pseudocode`) to ~10 Ep2 apps; layer
   *fill-blank* and *spot-the-bug* (auto-mutation).
3. Add **daily goal + streak + XP**; ship the **practice queue** with simple SM-2 scheduling over
   `(concept, type)` mastery.
4. Expand generators (dry-run, pseudocode↔code, edge-case) and backfill specs across all episodes.
5. Tune difficulty/weighting from real miss-rate telemetry.

> The platform's existing split — **UI written once, functionality as a pure function, validated by
> an oracle** — is already the perfect substrate for generated exercises. Daily practice is mostly a
> *presentation + scheduling* layer over assets the course already produces.

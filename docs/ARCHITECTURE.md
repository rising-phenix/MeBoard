# Architecture

## High-level dependency direction

`app` -> `engine-api` -> `engine-*` implementations

`app` may depend on Android SDK and AndroidX. Core engine modules must remain pure Kotlin/JVM and must not import Android classes. Persistence adapters may use Room in an Android-specific data module.

## Proposed modules
- `:app` — Android application, IME service, keyboard views, settings and Android lifecycle.
- `:engine:api` — stable interfaces and domain models shared by engine components.
- `:engine:dictionary` — lexicon lookup, prefix index and personal dictionary abstractions.
- `:engine:learning` — token acceptance, frequency updates, n-gram counts and privacy gates.
- `:engine:prediction` — candidate generation, scoring, ranking and correction.
- `:engine:algorithms` — from-scratch Kotlin algorithm implementations, each with explicit contracts.
- `:data:android` — Room/SQLite entities, DAOs, migrations and repository implementations.
- `:tests:*` — independent tests; not runtime dependencies of `:app`.
- `:benchmarks:*` — JVM benchmarks and fixed test corpora; not runtime dependencies.

## Runtime flow
1. Android invokes `InputMethodService`.
2. IME reads editor metadata and classifies the field.
3. Privacy gate decides whether learning and suggestions are allowed.
4. UI sends a bounded prefix/context request to the prediction interface.
5. Prediction engine generates candidates from dictionary and local statistics.
6. Ranking stage returns a small ordered list.
7. User selects a candidate or continues typing.
8. On committed eligible text, learning updates local aggregate counts.
9. Database writes are asynchronous and batched where safe.

## Threading
- UI rendering and `InputConnection` operations stay on the main thread as required by Android.
- Tokenization, prefix lookup, ranking and database work run off the main thread.
- Every prediction request carries a sequence number; stale results are discarded.
- Debounce or cancel expensive work after rapid keystrokes.
- Keep keypress feedback immediate; never wait for model training.

## Boundaries
- UI does not directly query Room.
- Prediction code does not know about Android `InputConnection`.
- Learning policy is checked before data reaches the learning repository.
- The database stores aggregate counts and dictionary entries, not full raw typing history.
- Algorithm implementations expose deterministic inputs/outputs for unit tests.

## Error handling
- Database failure must not crash typing.
- Prediction timeout returns no suggestions rather than freezing input.
- Invalid or unknown editor metadata defaults to conservative behavior.
- Migration failure must preserve a recoverable backup where feasible and offer reset.

## Architecture decision records
Record significant choices in `docs/adr/` using `ADR_TEMPLATE.md`. Examples: storage schema, tokenizer strategy, ranking features, language model scope and minimum Android version.

# MeBoard

**Your words. Your keyboard. Your rules.**

MeBoard is a privacy-first, offline-first Android keyboard built with Kotlin. It learns a user's vocabulary locally and provides personalized suggestions without sending typed content to a server.

> Status: planning / engineering scaffold. This repository is not yet a working keyboard. Do not advertise features as implemented until their acceptance criteria pass.

## Core principles
- Native Android IME (`InputMethodService`), not an overlay or accessibility-based keylogger.
- Kotlin-only production implementation. No Python runtime, Python ML libraries, cloud inference, or mandatory external AI API.
- Prediction and learning are independent of the Android UI.
- Local-first storage, data minimization, explicit learning controls.
- Tests and benchmarks are separate from production application source.
- Experimental algorithms must prove value against a simple baseline before becoming production defaults.

## Repository map
- `app/`: Android application and IME UI only.
- `engine/`: reusable pure-Kotlin learning and prediction modules.
- `tests/`: isolated test modules; never packaged into the release APK.
- `benchmarks/`: repeatable JVM performance and quality benchmarks.
- `docs/`: public product, architecture, privacy, algorithm and release documentation.
- `milestones/`: versioned delivery plans and acceptance criteria.
- `backlog/`: public engineering work items.
- `AI_WORKSPACE/`: local-only agent instructions, prompts, scratch plans and logs. Git-ignored by design.

## Build policy
The release APK is built from `:app:assembleRelease` and its production dependencies. Test modules are not APK runtime dependencies and are not included in the release artifact.

The exact Gradle configuration is to be added in Milestone M0. See `docs/BUILD_AND_MODULES.md`.

## Current target
- Minimum Android API: 28 (Android 9), subject to device validation.
- Language: Kotlin.
- UI: native Android Views initially.
- Persistence: Room / SQLite through a repository boundary.
- Prediction: deterministic Kotlin algorithms first; optional small neural model only after benchmark evidence.

## Start here
1. Read `docs/PRODUCT_SPEC.md`.
2. Read `docs/ARCHITECTURE.md`.
3. Read `docs/PRIVACY_AND_SECURITY.md`.
4. Read `milestones/M0_FOUNDATION.md`.
5. Follow the local-only `AI_WORKSPACE/AGENTS.md` after extracting this package.
6. Pick one issue from `backlog/` and complete its acceptance criteria.

## License
Choose and add a license before the first public source release. The current recommendation is GPL-3.0-or-later if the project wants redistributed derivatives to remain open source. This is a project decision, not yet a legal determination.

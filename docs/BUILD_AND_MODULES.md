# Build and module boundaries

## Production APK boundary
The release APK is produced by the Android `:app` module plus its declared runtime dependencies. Tests, fixtures, benchmark harnesses, local prompts and agent logs must not be runtime dependencies.

Expected command:
```bash
./gradlew :app:assembleDebug
./gradlew :app:assembleRelease
```

Tests are run explicitly:
```bash
./gradlew test
./gradlew :tests:engine:test
```

Exact task names depend on the final Gradle module setup.

## Suggested repository layout
```text
MeBoard/
├── app/                         # Android app and IME production code
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/dev/meboard/app/
│       │   ├── ime/
│       │   ├── keyboard/
│       │   ├── settings/
│       │   └── lifecycle/
│       └── res/
├── engine/
│   ├── api/
│   ├── algorithms/
│   ├── dictionary/
│   ├── learning/
│   └── prediction/
├── data/
│   └── android/
├── tests/                       # Separate test modules and fixtures
│   ├── engine/
│   ├── app-unit/
│   ├── ime-instrumentation/
│   ├── privacy/
│   └── fixtures/
├── benchmarks/
│   ├── prediction/
│   └── storage/
├── docs/
├── milestones/
├── backlog/
├── .github/
├── AI_WORKSPACE/                # Local only; .gitignore excludes it
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

## Important Gradle rule
Do not make `:app` depend on any test or benchmark module. A test module may depend on production modules; the reverse dependency is forbidden.

The Android instrumentation test harness may target the app but must remain a separate test APK/task. Verify that `:app:assembleRelease` does not invoke or package test code.

## Dependency policy
- Prefer AndroidX and Kotlin standard libraries.
- Avoid large ML runtimes in the initial release.
- Pin plugin and dependency versions.
- Commit Gradle wrapper files.
- Use dependency verification / locking when practical.
- No dynamic dependency versions.
- Every dependency needs a written reason and maintenance assessment.

## Build verification
CI must run formatting, compilation, unit tests and a debug APK build. Release signing keys must never be stored in GitHub source.

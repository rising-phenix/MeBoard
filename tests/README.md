# Separate test workspace

This directory is intentionally separate from production source. Test modules may depend on production modules; production modules must never depend on test modules.

Suggested modules:
- `engine/`: pure Kotlin/JVM unit and property tests.
- `app-unit/`: Android app unit tests that do not require device runtime.
- `ime-instrumentation/`: device/emulator tests for IME lifecycle and editor behavior.
- `privacy/`: sensitive-field, pause, deletion and logging tests.
- `fixtures/`: synthetic, licensed and non-sensitive fixtures only.

Do not place personal typing history in this tree. Do not make `:app` depend on any test module. Release APK builds must exclude all test classes and fixtures.

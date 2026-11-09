# M0 — Repository and build foundation

## Goal
Create a clean, reproducible Kotlin Android project with strict production/test/private-workspace boundaries.

## Tasks
- [ ] Create Git repository and choose project license before public source publication.
- [ ] Add Gradle wrapper and pinned plugin versions.
- [ ] Create `:app`, `:engine:*` and `:data:android` production modules.
- [ ] Create separate `tests/` modules.
- [ ] Create `benchmarks/` module(s).
- [ ] Add `.gitignore` rules for `AI_WORKSPACE/`, local data, build output and secrets.
- [ ] Add CI for formatting, compilation, unit tests and debug APK.
- [ ] Add dependency policy and ADR template.
- [ ] Verify `:app:assembleRelease` does not package or depend on tests.
- [ ] Verify no Python source, Python runtime or Python ML dependencies exist in product modules.

## Acceptance criteria
- Fresh clone builds using documented commands.
- CI passes on a clean runner.
- `app` does not depend on tests or benchmarks.
- AI workspace is ignored by Git.
- No signing keys or local user data are tracked.
- Module dependency graph is documented.

## Evidence
Attach CI run, dependency graph and APK artifact metadata to the milestone issue.

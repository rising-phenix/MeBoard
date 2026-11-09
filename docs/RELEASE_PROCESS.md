# Release process

## Versioning
Use semantic versioning after the first public release:
- MAJOR: incompatible behavior or data changes.
- MINOR: backward-compatible features.
- PATCH: compatible fixes.

## Release checklist
- [ ] Milestone acceptance criteria complete.
- [ ] CI green on the release commit.
- [ ] Unit, integration and instrumentation tests reviewed.
- [ ] Privacy and manifest audit complete.
- [ ] Performance benchmarks recorded on target hardware.
- [ ] Database migrations tested from supported prior versions.
- [ ] Changelog and known issues updated.
- [ ] Release APK built from a tagged commit.
- [ ] APK SHA-256 checksum published.
- [ ] Signing and provenance documented.
- [ ] Install, upgrade and rollback notes written.
- [ ] Source tag matches the APK build commit.

Do not publish a release claiming unsupported compatibility or unverified performance.

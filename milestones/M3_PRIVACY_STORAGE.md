# M3 — Privacy gate and local storage

## Tasks
- [ ] Define editor input classification.
- [ ] Add conservative sensitive-field exclusion.
- [ ] Add global learning pause.
- [ ] Add Room schema for personal dictionary and aggregate counts.
- [ ] Add repository interface and migration tests.
- [ ] Add add/edit/delete custom word controls.
- [ ] Add clear-all learned data action.
- [ ] Add settings explanation of learning behavior.
- [ ] Audit logs, manifest, backup and dependencies.

## Acceptance criteria
- Learning is disabled for password/sensitive fields.
- Pause prevents all learning writes.
- Clear-all removes all learned aggregates and custom entries as specified.
- No raw sentences or raw keystroke logs are stored.
- Database access never blocks keyboard rendering.
- Migration and reset tests pass.

## Dependencies
M0–M2 complete.

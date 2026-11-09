# M1 — Native IME foundation

## Goal
A minimal keyboard can be enabled, selected and used for basic text entry.

## Tasks
- [ ] Declare IME service and metadata in manifest/resources.
- [ ] Implement `InputMethodService` lifecycle.
- [ ] Implement a minimal custom keyboard view.
- [ ] Implement letter input and delete.
- [ ] Implement space and enter/editor action.
- [ ] Implement shift and basic symbols.
- [ ] Handle composing text and selection replacement correctly.
- [ ] Handle keyboard hide/show and IME switching.
- [ ] Test on API 28 emulator/device and a current Android version.

## Acceptance criteria
- MeBoard appears in Android keyboard settings.
- User can enable and select it.
- Basic typing works in plain text fields.
- Backspace, cursor/selection and editor actions behave correctly.
- No crash on hide/show, orientation change or app switch.
- No learning or prediction is implemented yet.

## Dependencies
M0 complete.

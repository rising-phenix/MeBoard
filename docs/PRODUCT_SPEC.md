# Product specification

## Product statement
MeBoard is a native Android keyboard that lets people type comfortably while building a private, on-device vocabulary model from eligible typing interactions.

## Primary users
- People who want suggestions adapted to their own vocabulary.
- People who prefer offline software and local data ownership.
- Users of older Android devices who need a lightweight keyboard.
- Developers who want a transparent, hackable Android IME.

## Non-goals for the first public release
- Cloud synchronization.
- Generative LLM completion.
- Speech recognition.
- Clipboard monitoring.
- Accessibility-service monitoring.
- Reading content from other apps outside the active input connection.
- Automatic upload of diagnostics or typing samples.
- A promise to support every language at launch.

## Functional requirements
1. The user can enable MeBoard in Android input settings and select it as the active IME.
2. The keyboard supports letters, delete, shift, space, enter/editor actions, symbols and IME switching.
3. The keyboard responds to editor configuration and input type.
4. The user can choose theme, keyboard height and suggestion visibility.
5. Learning is local and can be paused, resumed, reset and disabled per supported context.
6. The user can add, edit and delete personal dictionary entries.
7. Suggestions are ranked locally using deterministic, testable algorithms.
8. Sensitive fields are excluded from learning and personalization.
9. The user can inspect and erase stored learning data.
10. The app remains usable with learning and suggestions disabled.

## Non-functional requirements
- No network permission in the initial release unless a documented feature later requires it.
- No raw typed-text logging.
- No raw sentence corpus stored by default.
- Predictive work must not block the IME main thread.
- Bounded memory, database size and per-keystroke compute.
- Graceful operation when the local database is empty or corrupt.
- Reproducible builds and automated tests.
- Accessible key labels, touch targets and contrast.

## UX defaults
- Conservative, uncluttered QWERTY layout.
- Three monochrome themes: dark, light and graphite.
- Suggestions enabled only after the user understands learning controls.
- Learning state visible in settings and quickly accessible.
- No misleading "AI" claims; explain what the keyboard learns.

## Release acceptance
A release may claim a feature only when its linked milestone acceptance criteria and tests pass on the supported device/API matrix.

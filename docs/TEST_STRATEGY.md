# Test strategy

Tests live under the separate top-level `tests/` tree. Production modules must not depend on tests.

## Test layers
1. **Unit tests:** tokenizer, trie, counts, scoring, regression, trees, clustering, ANN math.
2. **Property tests:** normalization invariants, count non-negativity, deterministic sorting, trie/prefix consistency.
3. **Integration tests:** learning pipeline + repository + prediction pipeline.
4. **Database tests:** migrations, corruption handling, deletion and reset.
5. **Android instrumentation:** IME lifecycle, editor actions, input types, rotation/resizing, keyboard switching.
6. **Privacy tests:** password/OTP exclusions, pause, deletion, no raw-text logging.
7. **Performance tests:** per-keystroke latency, allocations, memory, database growth, cold start.
8. **Manual device tests:** actual curved-edge layout, touch accuracy, landscape, one-handed use and accessibility.

## Required test cases
- Empty dictionary.
- One-word and very large dictionaries.
- Unicode, apostrophes, punctuation and mixed case.
- Rapid typing and stale prediction responses.
- Backspace and cursor movement.
- Selection replacement and composing text.
- Password and visible-password variations.
- Number, phone, email and URI input types.
- Editor action variations (Go, Search, Send, Next, Done).
- Database migration from every supported schema.
- Learning paused / reset / app restart.
- Device rotation, multi-window and system insets.

## Test data
Use synthetic or explicitly licensed public corpora. Never commit real personal typing histories. Test fixtures must be small, documented and non-sensitive.

## Quality gates
A feature is not done because it compiles. It is done when acceptance criteria, tests, privacy checks and target-device behavior are verified.

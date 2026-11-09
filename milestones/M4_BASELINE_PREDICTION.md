# M4 — Baseline prediction engine

## Tasks
- [ ] Implement tokenizer/normalizer.
- [ ] Implement dictionary API and prefix trie/index in Kotlin.
- [ ] Implement unigram counts.
- [ ] Implement bigram counts.
- [ ] Implement candidate generation.
- [ ] Implement deterministic scoring and tie-breaking.
- [ ] Add sequence numbers/cancellation to discard stale results.
- [ ] Add quality and latency benchmarks.

## Acceptance criteria
- Suggestions work offline from local data.
- Empty or sparse user data does not break typing.
- Results are deterministic for identical model state and context.
- Prediction meets documented latency target on target device or has an approved revised budget.
- No network permission or remote inference is required.

## Dependencies
M3 complete.

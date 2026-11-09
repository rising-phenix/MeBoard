# M5 — Context prediction and correction

## Tasks
- [ ] Add bounded trigram counts.
- [ ] Add recency/frequency scoring only with documented rationale.
- [ ] Add casing and punctuation features.
- [ ] Prototype edit-distance correction with strict candidate bounds.
- [ ] Add acceptance/rejection feedback.
- [ ] Evaluate top-1/top-3 accuracy, MRR, correction precision and false-correction rate.

## Acceptance criteria
- Context improves ranking against the M4 baseline on held-out opt-in data.
- False corrections remain within an agreed threshold.
- Correction can be disabled independently.
- CPU, memory and latency budgets remain acceptable.

## Dependencies
M4 complete.

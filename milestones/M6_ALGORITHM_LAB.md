# M6 — From-scratch Kotlin algorithm lab

## Goal
Evaluate advanced algorithms without forcing unsuitable algorithms into the production path.

## Experiments
- [ ] Logistic regression: candidate acceptance/ranking probability.
- [ ] Linear regression: score/confidence calibration where a valid numeric target exists.
- [ ] Decision tree: bounded context classification.
- [ ] K-means: cluster coarse usage contexts or domains; evaluate whether clusters improve candidate selection.
- [ ] Small feed-forward ANN: optional reranker or next-token experiment.
- [ ] Naive Bayes: lightweight context classifier baseline.

## Rules
- Implement algorithms in Kotlin from scratch.
- Keep each experiment in `engine/algorithms` or a clearly isolated experimental module.
- Do not add Python, Python ML libraries, PyTorch or TensorFlow.
- Use synthetic or explicitly opt-in datasets; no private raw typing corpus in Git.
- Fix random seeds for reproducibility.
- Compare against M4/M5 baseline.
- Record memory, p50/p95 latency and quality metrics.

## Acceptance criteria
Each experiment has mathematical notes, API contract, complexity analysis, deterministic tests and benchmark report. An experiment is promoted only if it demonstrably improves quality without unacceptable cost or privacy risk.

## Dependencies
M4 complete; M5 recommended.

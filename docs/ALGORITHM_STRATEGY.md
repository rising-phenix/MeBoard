# Algorithm strategy: Kotlin from scratch

## Guiding principle
Implement the core algorithms in Kotlin from scratch, with transparent data structures and unit tests. Do not use Python, scikit-learn, TensorFlow, PyTorch, hosted inference or opaque model downloads in the product.

"From scratch" means our own Kotlin implementation and tests. It does not mean inventing unsafe math or avoiding standard Kotlin/JVM primitives. Every algorithm must be compared against a simpler baseline.

## Production baseline (first)
1. Tokenizer and normalizer with language-aware boundaries.
2. Personal dictionary backed by a prefix trie or sorted prefix index.
3. Unigram frequency counts.
4. Bigram and later trigram counts with bounded context.
5. Candidate generation from prefix matches.
6. Candidate ranking using transparent weighted features.
7. Optional edit-distance correction after explicit evaluation.

This baseline is interpretable, fast, small and likely more useful than an unproven neural model.

## Algorithm fit matrix

| Algorithm | Potential use | Status |
|---|---|---|
| Trie / prefix index | Fast completion candidates | Core |
| N-gram / Markov counts | Next-word context | Core |
| Frequency ranking | Personal vocabulary adaptation | Core |
| Levenshtein distance | Typo correction | Optional, bounded |
| Logistic regression | Learn candidate acceptance / ranking weights | Experiment |
| Linear regression | Calibrate numeric ranking or confidence | Experiment; not a standalone language model |
| Decision tree | Context-based selection such as punctuation, casing or editor mode | Experiment |
| K-means | Cluster usage contexts or vocabulary domains | Research only; not direct next-word prediction |
| Small feed-forward ANN | Experimental candidate reranking or next-token model | Research only |
| Naive Bayes | Lightweight context classification | Experiment |

## Evaluation gate
No experimental algorithm becomes a production default unless it:
- Beats the baseline on a held-out, opt-in test corpus.
- Has a measurable metric improvement (top-1/top-3 accuracy, MRR, correction precision/recall).
- Meets latency and memory budgets on the oldest target device.
- Has deterministic unit tests and documented failure modes.
- Does not require retaining raw typed sentences by default.
- Can be disabled and removed without breaking basic typing.

## Implementing algorithms from scratch
Each implementation must include:
- Mathematical definition and assumptions.
- Kotlin API and immutable input/output contracts where practical.
- Complexity analysis (time and memory).
- Numerical stability considerations.
- Seeded deterministic behavior for randomized algorithms.
- Unit tests for edge cases and known examples.
- Benchmark against the simplest baseline.
- Documentation of why the algorithm belongs in a keyboard.

## Important cautions
- K-means is unsupervised clustering; clusters do not inherently map to likely next words.
- Regression predicts numeric values; it needs a well-defined target such as acceptance probability or score calibration.
- A decision tree can overfit sparse personal data; cap depth and require minimum samples.
- An ANN can consume more RAM and battery than n-grams; quantization or a small custom implementation may be considered later.
- Do not train on password, OTP, payment, private or explicitly excluded fields.
- Do not infer that a user's private writing style is safe to export.

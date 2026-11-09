# Performance and resource budgets

These are initial engineering targets, not measured claims. Record actual measurements and revise targets with an ADR.

| Metric | Initial target |
|---|---:|
| Candidate response latency | p95 under 100 ms |
| Main-thread prediction work | No blocking model/database work |
| Candidate count returned | 3–5 by default |
| Background work | Bounded and cancellable |
| Memory | Measure on oldest supported device; avoid large model loads |
| APK size | Track every release; aim under 20 MB initially |
| Local database | Bounded growth with clear/reset controls |
| Network use | Zero in initial release |

## Benchmark protocol
- Use fixed synthetic dictionaries and contexts.
- Warm up JVM code before timing.
- Record device model, Android version, build variant and dataset size.
- Report median and p95, not just the fastest run.
- Benchmark cold start and warm prediction separately.
- Include rapid typing and cancellation scenarios.
- Keep benchmark code and result files out of the APK.

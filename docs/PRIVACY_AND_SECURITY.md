# Privacy and security requirements

## Threat model
A keyboard can observe text entered through the active input connection. Treat this as highly sensitive data. The product must assume that users may type passwords, health information, financial details, private messages and authentication codes.

## Mandatory rules
- No network permission in the first release.
- No transmission of typed content, learned words, frequency counts or prediction context.
- No raw keystroke or sentence logs.
- Do not use AccessibilityService to inspect other apps.
- Do not read clipboard contents.
- Detect Android password / sensitive input variations and disable learning and suggestions conservatively.
- Treat OTP, payment, private and unknown sensitive contexts conservatively.
- Provide a global learning pause, clear-all control and per-feature controls.
- Keep local data in app-private storage.
- Avoid exporting data unless the user explicitly requests it.
- Do not include personal dictionary content in crash reports or analytics.
- No analytics SDK in the initial release.

## Learning data minimization
Store only the minimum aggregates needed: normalized word, count, timestamps/coarse recency if justified, and bounded n-gram statistics. Avoid storing full sentences or raw editor contents.

## Security controls
- Validate all database inputs.
- Use parameterized database queries through Room.
- Apply schema migrations and migration tests.
- Keep debug logging free of typed content.
- Protect backups and define whether app backup is disabled or user-controlled.
- Do not commit signing keys, local databases, corpora or secrets.
- Review dependencies and Android manifest before release.

## User controls
Settings must explain:
- What is learned.
- Where it is stored.
- Which fields are excluded.
- How to pause learning.
- How to clear learned data.
- Whether dictionary export exists and what it contains.

## Release privacy checklist
- [ ] Manifest reviewed; no `INTERNET` permission without approved ADR.
- [ ] Sensitive editor tests pass.
- [ ] No raw text in logs, exceptions, analytics or crash output.
- [ ] No unexpected network traffic.
- [ ] Clear-all actually deletes dictionary and statistics.
- [ ] Learning pause prevents writes.
- [ ] Backup behavior documented.
- [ ] Privacy policy matches implementation.

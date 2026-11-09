# Contributing

Thank you for considering a contribution to MeBoard.

## Before starting
- Read the product spec, architecture and privacy documents.
- Check existing issues and milestones.
- Discuss large changes before implementation.
- Do not submit raw personal typing data, secrets or private corpora.

## Pull request expectations
- One focused change per pull request.
- Link the issue and milestone.
- Explain behavior and design decisions.
- Include tests and benchmark evidence when relevant.
- Update documentation and changelog when behavior changes.
- State device/API versions tested.
- Confirm no raw typed text is logged or transmitted.

## Coding standards
- Kotlin for production and test code.
- Keep core engine modules Android-independent.
- Prefer small functions, explicit contracts and clear names.
- Avoid premature abstractions and unnecessary dependencies.
- Do not silently change database schemas or privacy behavior.
- No generated code committed without review and attribution where required.

## Definition of done
Acceptance criteria pass, tests pass, privacy review is complete, documentation is updated and the change is reviewable.

# M2 — Keyboard UX and device adaptation

## Tasks
- [ ] QWERTY layout with stable touch targets.
- [ ] Symbols/numbers layer and predictable layer switching.
- [ ] Dark, light and graphite themes.
- [ ] Adjustable keyboard height.
- [ ] Suggestions row placeholder (no prediction dependency yet).
- [ ] Respect system insets and navigation mode.
- [ ] Handle rounded/curved display edges by using safe content insets, not model-specific pixel hacks.
- [ ] Landscape and one-handed layout considerations.
- [ ] Settings screen and reset-to-defaults.

## Acceptance criteria
- No clipped keys on supported screen sizes.
- Touch targets remain usable at minimum and maximum height.
- Theme changes apply without restarting the device.
- Keyboard remains functional when suggestion row is hidden.
- Test evidence includes the actual target phone and at least one emulator profile.

## Dependencies
M1 complete.

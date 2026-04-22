# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

A 40-second play clock app for American football referees, targeting **Pixel Watch 2 / Wear OS 3+** (min SDK 30). Built with Kotlin and Jetpack Compose.

## Build & Deploy

```bash
# Build debug APK
./gradlew assembleDebug

# Install to connected device (watch must be connected via ADB)
./gradlew installDebug

# Build + install in one step
./gradlew assembleDebug && adb install app/build/outputs/apk/debug/app-debug.apk
```

**Device connection scripts** (PowerShell, for wireless ADB to physical watch):
```powershell
.\scripts\pair-watch.ps1       # One-time pairing
.\scripts\connect-watch.ps1    # Connect after pairing
.\scripts\disconnect-watch.ps1 # Disconnect
```

There are no automated tests or lint tasks configured.

## Architecture

Single-module Android app (`app/`) with two source files of substance:

**`MainActivity.kt`** — minimal entry point. Enables edge-to-edge, wraps everything in `PlayClockTheme { PlayClockScreen() }`.

**`ui/PlayClockScreen.kt`** — contains all business logic and UI. Key state:
- `activeDuration: Int?` — `null` = start screen; `25` or `40` = countdown mode
- `secondsLeft: Int` — current countdown value
- `isRunning: Boolean` — whether the tick loop is active

The composable renders two distinct views based on `activeDuration`:
1. **Start screen**: Split 25/40 buttons
2. **Running view**: Large countdown with color transitions (purple → orange <5s → red ≤0)

The countdown runs in a `LaunchedEffect` coroutine with `delay(1000)` ticks. Vibration escalates in the final 10 seconds (short pulse 10–6s, urgent double-pulse 5–1s). Power management uses a partial `WakeLock` + `FLAG_KEEP_SCREEN_ON`, both acquired/released via `DisposableEffect`.

**`ui/theme/Theme.kt`** — Material 3 dark theme (purple primary, cyan secondary).

## Key Details

- Vibration uses `VibratorManager` on Android S+ and falls back to the deprecated `Vibrator` for older APIs — both paths must be maintained.
- The double-tap gesture (switches 40→25 mid-countdown) is handled via `detectTapGestures` with a 300ms window, not the standard Compose click modifier.
- Target device is Wear OS (circular screen); layout uses `BoxWithConstraints` to size elements proportionally to screen dimensions rather than fixed dp values.

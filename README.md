# Play Clock (Wear OS)

American football play clock app for **Pixel Watch 2** (and other Wear OS 3+ devices), designed for referee use.

## Features

- **40-second countdown** by default (NFL-style; configurable in code for NCAA 25 seconds if needed).
- **Start / Stop** – run the clock when the ball is marked ready; stop when the play ends.
- **Reset** – reset to full time (shown when stopped).
- **Visual feedback** – countdown turns orange in the last 5 seconds and red at zero.

## Requirements

- **Android Studio** (Ladybug or newer recommended)
- **Wear OS 3.0+** device or emulator (e.g. Pixel Watch 2, API 30+)
- **JDK 17**

## Setup and run

1. Open the project in Android Studio: **File → Open** → select the `playclock` folder.
2. Wait for Gradle sync to finish.
3. **Device:**
   - **Emulator:** **Tools → Device Manager** → create a **Wear OS** virtual device (e.g. “Pixel Watch 2”).
   - **Physical watch:** Enable **Developer options** (Settings → System → About → tap **Versions** 7 times), then **USB debugging** (or Wi‑Fi debugging). Connect via USB or over the same Wi‑Fi as your PC.
4. Choose the Wear device in the run configuration and click **Run** (green triangle).

The app appears as **Play Clock** in the watch app list.

## Project structure

```
playclock/
├── app/                          # Wear OS app module
│   ├── src/main/
│   │   ├── java/.../referee/
│   │   │   ├── MainActivity.kt
│   │   │   └── ui/
│   │   │       ├── PlayClockScreen.kt   # Play clock UI and countdown logic
│   │   │       └── theme/Theme.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Customization

- **Default duration:** In `PlayClockScreen.kt`, change `DEFAULT_PLAY_CLOCK_SECONDS` (e.g. `40` for NFL, `25` for NCAA) or pass `initialSeconds` into `PlayClockScreen()`.
- **UI/behavior:** You can extend the screen (e.g. presets for 40/25, vibration at 5 sec or 0, always-on display) in the same file or new composables.

## License

Use and modify as you like; no warranty.

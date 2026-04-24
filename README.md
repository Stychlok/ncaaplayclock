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

## Install via APK (sideloading)

No Android Studio needed — use this if you just want to install the app from a pre-built APK file.

**On your phone:**
1. Install **Wear OS** from the Play Store (if not already there) and pair it with your watch.
2. Install **Android Debug Bridge (ADB)**. The easiest option is the small standalone package:  
   → Download "SDK Platform Tools" from developer.android.com/tools/releases/platform-tools and unzip it.

**On your watch:**
3. Go to **Settings → System → About** and tap **Build number** 7 times to enable Developer options.
4. Go to **Settings → Developer options** and turn on **ADB debugging** and **Debug over Wi-Fi**.
5. Note the IP address and port shown on screen (e.g. `192.168.1.42:12345`).

**Install:**
6. Open a terminal on your PC, navigate to the platform-tools folder, and run:
   ```
   adb connect <ip>:<port>
   adb install playclock.apk
   ```
7. Accept the pairing prompt on your watch if asked.

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

## Play Store listing

### Short description (max 80 characters)

```
40 s / 25 s play clock for American football referees on Wear OS.
```

### Long description

Play Clock is a minimalist play clock app for American football referees, built for Wear OS smartwatches.

Tap 25 or 40 to start the countdown — the screen stays on for the entire play so you never lose track.

**Features**
- 25-second clock (NCAA / college rules)
- 40-second clock (NFL / college rules)
- Switch from 40 → 25 mid-countdown with a double-tap when a penalty is called
- Color-coded countdown: purple → orange in the last 5 seconds → red at zero
- Escalating vibration: single pulse from 10 seconds down, double pulse in the final 5
- Screen stays on while the clock is running — no interruptions
- Reset button to cancel and return to the start screen

Designed for the wrist. No distractions, no setup — just tap and ref.

## License

Use and modify as you like; no warranty.

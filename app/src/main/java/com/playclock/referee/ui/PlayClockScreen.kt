package com.playclock.referee.ui

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import kotlinx.coroutines.delay

private const val CLOCK_25 = 25
private const val CLOCK_40 = 40
private const val VIBRATE_LAST_SECONDS = 10

@Composable
fun PlayClockScreen() {
    val context = LocalContext.current
    var activeDuration by remember { mutableStateOf<Int?>(null) } // 25 or 40 when running
    var secondsLeft by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning, activeDuration) {
        if (!isRunning || activeDuration == null) return@LaunchedEffect
        while (isRunning && secondsLeft > 0) {
            delay(1000)
            if (secondsLeft > 0) {
                secondsLeft -= 1
                // Vibration for the last 10 seconds (while 1..10 remaining); stop at 0
                if (secondsLeft in 1..VIBRATE_LAST_SECONDS) {
                    vibrateShort(context)
                }
            }
        }
        isRunning = false
        // At 0: return to start screen
        activeDuration = null
        secondsLeft = 0
    }

    if (activeDuration != null) {
        // Running: single clock with countdown, reset, and (when 40) switch to 25
        RunningView(
            duration = activeDuration!!,
            secondsLeft = secondsLeft,
            onReset = {
                activeDuration = null
                secondsLeft = 0
                isRunning = false
            },
            onSwitchTo25 = if (activeDuration == CLOCK_40) {
                {
                    activeDuration = CLOCK_25
                    secondsLeft = CLOCK_25
                    isRunning = true
                }
            } else null
        )
    } else {
        // Start screen: split — 25 left, 40 right, divider in middle
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            // Left: 25 seconds
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        activeDuration = CLOCK_25
                        secondsLeft = CLOCK_25
                        isRunning = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "25",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center
                )
            }
            // Vertical divider
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(Color.Gray)
            )
            // Right: 40 seconds
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        activeDuration = CLOCK_40
                        secondsLeft = CLOCK_40
                        isRunning = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "40",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFBB86FC),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun RunningView(
    duration: Int,
    secondsLeft: Int,
    onReset: () -> Unit,
    onSwitchTo25: (() -> Unit)?
) {
    val displayColor = when {
        secondsLeft <= 0 -> Color.Red
        secondsLeft <= 5 -> Color(0xFFFF9800)
        else -> Color(0xFFBB86FC)
    }

    val doubleTapModifier = if (onSwitchTo25 != null) {
        Modifier.pointerInput(Unit) {
            detectTapGestures(onDoubleTap = { onSwitchTo25() })
        }
    } else Modifier

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .then(doubleTapModifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top: which clock is running (same “icon” as start: the number 25 or 40)
        Text(
            text = duration.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Center: big remaining time (double-tap when 40 to switch to 25)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = secondsLeft.coerceAtLeast(0).toString(),
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = displayColor,
                textAlign = TextAlign.Center
            )
            Text(
                text = "sec",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Bottom: reset (↺) and, when 40 running, switch-to-25 button below
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onReset),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "↺",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (onSwitchTo25 != null) {
                Text(
                    text = "25",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFBB86FC),
                    modifier = Modifier.clickable(onClick = onSwitchTo25)
                )
            }
        }
    }
}

private fun vibrateShort(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)
            ?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && vibrator?.hasVibrator() == true) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }
}

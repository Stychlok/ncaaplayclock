package com.playclock.referee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.playclock.referee.ui.PlayClockScreen
import com.playclock.referee.ui.theme.PlayClockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayClockTheme {
                PlayClockScreen()
            }
        }
    }
}

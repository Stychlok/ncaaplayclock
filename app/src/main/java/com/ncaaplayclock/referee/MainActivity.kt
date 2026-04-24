package com.ncaaplayclock.referee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ncaaplayclock.referee.ui.PlayClockScreen
import com.ncaaplayclock.referee.ui.theme.PlayClockTheme

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

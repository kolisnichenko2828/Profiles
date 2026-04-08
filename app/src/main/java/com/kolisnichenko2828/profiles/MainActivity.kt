package com.kolisnichenko2828.profiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kolisnichenko2828.profiles.presentation.navigation.ProfilesApp
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilesTheme {
                ProfilesApp()
            }
        }
    }
}
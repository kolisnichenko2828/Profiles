package com.kolisnichenko2828.profiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kolisnichenko2828.profiles.presentation.navigation.ProfilesApp
import com.kolisnichenko2828.profiles.presentation.navigation.Screen
import com.kolisnichenko2828.profiles.presentation.theme.ProfilesTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilesTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                when {
                    uiState.isLoading -> Unit
                    uiState.isProfileExist -> {
                        ProfilesApp(
                            startScreen = Screen.Own
                        )
                    }
                    !uiState.isProfileExist -> {
                        ProfilesApp(
                            startScreen = Screen.Create
                        )
                    }
                }
            }
        }
    }
}
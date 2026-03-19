package com.kolisnichenko2828.profiles.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.kolisnichenko2828.profiles.presentation.create.CreateScreen
import com.kolisnichenko2828.profiles.presentation.details.DetailsScreen
import com.kolisnichenko2828.profiles.presentation.own.OwnScreen
import com.kolisnichenko2828.profiles.presentation.users.UsersScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProfilesApp(
    startScreen: Screen
) {
    val backStack = rememberNavBackStack(startScreen)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            sceneStrategy = listDetailStrategy,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Screen.Create> {
                    CreateScreen()
                }
                entry<Screen.Own> {
                    OwnScreen()
                }
                entry<Screen.Users>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Select user")
                            }
                        }
                    ),
                    content = {
                        UsersScreen(
                            onUserClick = { id ->
                                if (backStack.lastOrNull() is Screen.Details ) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(Screen.Details(id))
                            }
                        )
                    }
                )
                entry<Screen.Details>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    DetailsScreen(id = it.id)
                }
            }
        )
    }
}

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    object Users : Screen
    @Serializable
    data class Details(val id: Int) : Screen

    @Serializable
    object Own : Screen

    @Serializable
    object Create : Screen
}
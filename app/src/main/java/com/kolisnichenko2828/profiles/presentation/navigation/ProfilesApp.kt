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
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.ProfileEditScreen
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactDetailsScreen
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.ProfileDetailsScreen
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.ContactsListScreen
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
                entry<Screen.ProfileEdit> {
                    ProfileEditScreen(
                        onNavigateToDetails = {
                            backStack.clear()
                            backStack.add(Screen.ProfileDetails)
                        }
                    )
                }
                entry<Screen.ProfileDetails> {
                    ProfileDetailsScreen(
                        onEditClick = {
                            backStack.add(Screen.ProfileEdit)
                        }
                    )
                }
                entry<Screen.ContactsList>(
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
                        ContactsListScreen(
                            onUserClick = { id ->
                                if (backStack.lastOrNull() is Screen.ContactDetails ) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(Screen.ContactDetails(id))
                            }
                        )
                    }
                )
                entry<Screen.ContactDetails>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    ContactDetailsScreen(id = it.id)
                }
            }
        )
    }
}

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    object ContactsList : Screen
    @Serializable
    data class ContactDetails(val id: Int) : Screen

    @Serializable
    object ProfileDetails : Screen

    @Serializable
    object ProfileEdit : Screen
}
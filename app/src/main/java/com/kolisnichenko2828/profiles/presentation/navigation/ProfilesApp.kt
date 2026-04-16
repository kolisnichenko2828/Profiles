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
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.kolisnichenko2828.profiles.presentation.screens.contactslist.ContactsListScreen
import com.kolisnichenko2828.profiles.presentation.screens.profiledetails.ProfileDetailsScreen
import com.kolisnichenko2828.profiles.presentation.screens.editprofile.ProfileEditScreen
import com.kolisnichenko2828.profiles.presentation.screens.addcontact.AddContactScreen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProfilesApp() {
    val backStack = rememberNavBackStack(Screen.ContactsList)
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.fillMaxSize(),
            sceneStrategy = listDetailStrategy,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Screen.ProfileEdit> {
                    ProfileEditScreen(
                        onNavigateToDetails = {
                            backStack.clear()
                            backStack.add(Screen.ProfileDetails)
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<Screen.ProfileDetails> {
                    ProfileDetailsScreen(
                        onEditClick = { backStack.add(Screen.ProfileEdit) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<Screen.ContactsList>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = {
                            Box(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Select user")
                            }
                        }
                    )
                ) {
                    ContactsListScreen(
                        onAddClick = { backStack.add(Screen.AddContact) },
                        onProfileClick = { backStack.add(Screen.ProfileDetails) }
                    )
                }
                entry<Screen.AddContact> {
                    AddContactScreen(
                        onNavigateToContacts = {
                            backStack.subList(1, backStack.size).clear()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        )
    }
}

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    object AddContact : Screen
    @Serializable
    object ContactsList : Screen
    @Serializable
    object ProfileDetails : Screen
    @Serializable
    object ProfileEdit : Screen
}
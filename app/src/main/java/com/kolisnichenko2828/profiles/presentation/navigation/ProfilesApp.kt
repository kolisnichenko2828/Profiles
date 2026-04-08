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
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_details.ContactDetailsScreen
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contact_edit.ContactEditScreen
import com.kolisnichenko2828.profiles.presentation.screens.contacts.contacts_list.ContactsListScreen
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_details.ProfileDetailsScreen
import com.kolisnichenko2828.profiles.presentation.screens.profile.profile_edit.ProfileEditScreen
import com.kolisnichenko2828.profiles.presentation.screens.random.RandomScreen
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
                        onContactClick = { backStack.add(Screen.ContactDetails(it)) },
                        onAddClick = { backStack.add(Screen.Random) },
                        onProfileClick = { backStack.add(Screen.ProfileDetails) }
                    )
                }
                entry<Screen.ContactDetails>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) {
                    ContactDetailsScreen(
                        id = it.id,
                        onEditClick = { backStack.add(Screen.ContactEdit(it.id)) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<Screen.ContactEdit> {
                    ContactEditScreen(
                        id = it.id,
                        isNew = it.isNew,
                        onNavigateToContacts = {
                            backStack.subList(1, backStack.size).clear()
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                entry<Screen.Random> {
                    RandomScreen(
                        onContactClick = {
                            backStack.add((Screen.ContactEdit(id = it, isNew = true)))
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
    object Random : Screen
    @Serializable
    class ContactEdit(val id: String, val isNew: Boolean = false) : Screen
    @Serializable
    object ContactsList : Screen
    @Serializable
    class ContactDetails(val id: String) : Screen
    @Serializable
    object ProfileDetails : Screen
    @Serializable
    object ProfileEdit : Screen
}
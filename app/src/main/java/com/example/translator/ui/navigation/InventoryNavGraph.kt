package com.example.translator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translator.data.Option
import com.example.translator.data.ConfigRepository
import com.example.translator.data.Word
import com.example.translator.ui.change.ChangeScreen
import com.example.translator.ui.config.ConfigScreen
import com.example.translator.ui.config.ConfigViewModel
import com.example.translator.ui.config.ConfigViewModelFactory
import com.example.translator.ui.home.HomeScreen

object HomeDestination {
    const val route = "home"
}

object ChangeDestination {
    const val route = "change"
}

object ConfigDestination {
    const val route = "config"
}

@Composable
fun InventoryNavHost(
    navViewModel: NavViewModel,
    configRepository: ConfigRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(HomeDestination.route) {
            val words by navViewModel.words.collectAsState()
            val option by configRepository.configLayout.collectAsState(initial = Option.BOTH)

            HomeScreen(
                words = words,
                option = option,
                onAddClick = {
                    navViewModel.set(null)
                    navController.navigate(ChangeDestination.route)
                },
                onEditClick = { word ->
                    navViewModel.set(word)
                    navController.navigate(ChangeDestination.route)
                },
                onDeleteClick = { word ->
                    navViewModel.delete(word)
                },
                onSettingsClick = {
                    navController.navigate(ConfigDestination.route)
                }
            )
        }

        composable(ChangeDestination.route) {
            ChangeScreen(
                word = navViewModel.editingWord,
                onInsertClick = { english, mongolia ->
                    navViewModel.editingWord?.let { existingWord ->
                        navViewModel.update(existingWord.copy(
                            english = english,
                            mongolia = mongolia
                        ))
                    } ?: run {
                        navViewModel.add(Word(english = english, mongolia = mongolia))
                    }
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(ConfigDestination.route) {
            val configViewModel: ConfigViewModel = viewModel(
                factory = ConfigViewModelFactory(configRepository)
            )

            ConfigScreen(
                viewModel = configViewModel,
                onCancelClick = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

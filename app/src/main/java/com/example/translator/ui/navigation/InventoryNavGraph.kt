package com.example.translator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.translator.ui.change.ChangeScreen
import com.example.translator.ui.config.ConfigScreen
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate("${ChangeDestination.route}?isEdit=false") },
                navigateToItemUpdate = { id -> navController.navigate("${ChangeDestination.route}?isEdit=true&itemId=$id") },
                navigateToConfig = { navController.navigate(ConfigDestination.route) }
            )
        }

        composable(route = ConfigDestination.route) {
            ConfigScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "${ChangeDestination.route}?isEdit={isEdit}&itemId={itemId}",
            arguments = listOf(
                navArgument("isEdit") {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument("itemId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        )
        { backStackEntry ->
            val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1

            ChangeScreen(
                isEditing = isEdit,
                itemId = if (isEdit) itemId else null,
                onCancelClick = { navController.popBackStack() },
                onInsertClick = { navController.popBackStack() }
            )
        }
    }
}

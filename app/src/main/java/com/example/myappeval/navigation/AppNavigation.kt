package com.example.myappeval.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myappeval.CharacterDetailScreen
import com.example.myappeval.CharacterListScreen
import com.example.myappeval.viewmodel.CharacterViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST,
        modifier = modifier
    ) {
        composable(Routes.LIST) {
            val state by viewModel.listState.collectAsState()
            CharacterListScreen(
                state = state,
                onRetry = { viewModel.loadCharacters() },
                onCharacterClick = { id ->
                    navController.navigate(Routes.detail(id))
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: 0
            val state by viewModel.detailState.collectAsState()

            LaunchedEffect(id) {
                viewModel.loadCharacter(id)
            }

            CharacterDetailScreen(
                state = state,
                onRetry = { viewModel.loadCharacter(id) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
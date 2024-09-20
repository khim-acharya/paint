package com.example.portablemoviestudio.navigation

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.portablemoviestudio.screens.HomeScreenUI
import com.example.portablemoviestudio.screens.MovieDetailScreenUI
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel

@Composable
fun MainScreenNavHost(
    mainNavController: NavHostController,
    bottomNavController: NavHostController,
    startDestination: String = MainScreenNavigationItems.Home.route,
    resources: Resources,
    sharedPreferences: SharedPreferences,
    mainViewModel: HomeScreenViewModel
) {
    NavHost(navController = mainNavController, startDestination = startDestination){
        composable(startDestination){
            HomeScreenUI(mainNavController = mainNavController, bottomNavController = bottomNavController, sharedPreferences = sharedPreferences, mainViewModel = mainViewModel)
        }
        composable("${MainScreenNavigationItems.Detail.route}/{movieId}", arguments = listOf(
            navArgument("movieId") {type = NavType.StringType}
        )) {
                backStackEntry ->
            backStackEntry.arguments?.getString("movieId")
                ?.let { MovieDetailScreenUI(navController = mainNavController, movieId = it, mainViewModel = mainViewModel) }
        }

    }
}
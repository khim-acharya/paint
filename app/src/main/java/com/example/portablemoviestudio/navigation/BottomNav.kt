package com.example.portablemoviestudio.navigation

import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.portablemoviestudio.data.FavoriteIcon
import com.example.portablemoviestudio.data.HomeICon
import com.example.portablemoviestudio.screens.LikedDataList
import com.example.portablemoviestudio.screens.LiveDataList
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel

sealed class BottomNavScreen(val route: String, val icon: ImageVector) {
    object HomeScreen: BottomNavScreen(Screen.HOMESCREEN.name, HomeICon)
    object FebScreen: BottomNavScreen(Screen.FEBSCREEN.name,FavoriteIcon)
}

val BottomNavItems = listOf(
    BottomNavScreen.HomeScreen,
    BottomNavScreen.FebScreen
)

@Composable
        fun BottomNavHost(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController,
    bottomNavController: NavHostController,
    startDestination: String = BottomNavScreen.HomeScreen.route,
    viewModel: HomeScreenViewModel,
    sharedPreferences: SharedPreferences
) {
    NavHost(bottomNavController, startDestination = startDestination, modifier = modifier) {
        composable(BottomNavScreen.HomeScreen.route) {
            LiveDataList(mainNavController = mainNavController, viewModel = viewModel,
                sharedPreferences = sharedPreferences
            )
        }
        composable(BottomNavScreen.FebScreen.route) {
            LikedDataList(mainNavController = mainNavController,viewModel = viewModel,
                sharedPreferences = sharedPreferences
            )
        }
    }
}
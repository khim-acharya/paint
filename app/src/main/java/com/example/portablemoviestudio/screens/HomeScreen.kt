package com.example.portablemoviestudio.screens

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.portablemoviestudio.data.BottomNavIconSize
import com.example.portablemoviestudio.data.DarkGray
import com.example.portablemoviestudio.data.FavoriteCountFontSize
import com.example.portablemoviestudio.data.FavoriteCountSize
import com.example.portablemoviestudio.data.Gray
import com.example.portablemoviestudio.data.HomeICon
import com.example.portablemoviestudio.data.HomeScreen
import com.example.portablemoviestudio.data.LightGray
import com.example.portablemoviestudio.data.LikedMovieScreen
import com.example.portablemoviestudio.data.Red
import com.example.portablemoviestudio.data.RefreshIcon
import com.example.portablemoviestudio.data.RefreshIconSize
import com.example.portablemoviestudio.data.Transparent
import com.example.portablemoviestudio.data.White
import com.example.portablemoviestudio.data.isFirst
import com.example.portablemoviestudio.entities.UiState
import com.example.portablemoviestudio.navigation.BottomNavHost
import com.example.portablemoviestudio.navigation.BottomNavItems
import com.example.portablemoviestudio.navigation.Screen
import com.example.portablemoviestudio.viewModels.HomeScreenViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    mainNavController: NavHostController,
    bottomNavController: NavHostController,
    sharedPreferences: SharedPreferences,
    mainViewModel: HomeScreenViewModel
){
    // アプリ起動時一回のみサーバ通信実行
    if(isFirst) {
        mainViewModel.initialize()
        isFirst = false
    }
    // Bottomナビゲーションバーの一位状態保持
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    //　サーバ通信の状態監視
    val uiState by mainViewModel.uiState.collectAsState()
    // お気に入りりリストの数を監視
    val likedMovieNumber by mainViewModel.likedMovieCount.collectAsState()
    // アプリの画面作成
    Surface{
        Box {
            Scaffold(
                containerColor = LightGray,
                topBar = {
                        TopAppBar(
                            title = {
                                Text(text =
                                if(navBackStackEntry?.destination?.route == Screen.FEBSCREEN.name) LikedMovieScreen else HomeScreen,
                                    color = White)
                                    },
                            colors = TopAppBarColors(
                                DarkGray,
                                Transparent,
                                Transparent,
                                Transparent,
                                Transparent),
                            actions = {
                                Row {
                                    IconButton(onClick = { mainViewModel.initialize() }) {
                                        Icon(
                                            imageVector = RefreshIcon,
                                            contentDescription = "",
                                            tint = White,
                                            modifier = Modifier.size(RefreshIconSize)
                                        )
                                    }
                                }
                            }
                        ) },
                bottomBar = {
                    BottomNavigation(backgroundColor = DarkGray) {
                        val currentDestination = navBackStackEntry?.destination
                        BottomNavItems.forEach{
                                screen ->
                            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } ?: false
                            BottomNavigationItem(
                                selected = selected,
                                onClick = {
                                        bottomNavController.navigate(screen.route) {
                                        popUpTo(bottomNavController.graph.findStartDestination().id)
                                    }
                                },
                                selectedContentColor = White,
                                icon = {
                                    if (screen.icon == HomeICon) {
                                        Box(modifier = Modifier.size(BottomNavIconSize),
                                            contentAlignment = Alignment.BottomEnd) {
                                            Icon(
                                                screen.icon,
                                                contentDescription = null,
                                                tint = if (selected) Color.White else Color.Gray
                                            )
                                        }
                                    } else {
                                        Box( modifier = Modifier.size(BottomNavIconSize)) {
                                            Box(modifier = Modifier.size(BottomNavIconSize),
                                                contentAlignment = Alignment.BottomEnd){
                                                Icon(
                                                    screen.icon,
                                                    contentDescription = null,
                                                    tint = if (selected) White else Gray
                                                )
                                            }
                                            // お気に入りり映画の数を表示するボックス
                                            if ((likedMovieNumber>0)  && !selected)
                                                Box(modifier = Modifier
                                                    .background(
                                                        color = Red,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .size(FavoriteCountSize),
                                                    contentAlignment = Alignment.Center){
                                                Text(text = likedMovieNumber.toString(),
                                                    color = White,
                                                    fontSize = FavoriteCountFontSize)
                                            }
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            ) {
                innerPadding ->
                // サーバ通信判断
                Box(Modifier.padding(innerPadding)) {
                    when(val state = uiState) {
                        is UiState.Loading ->
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        is UiState.Success -> {
                            BottomNavHost(
                                mainNavController = mainNavController,
                                bottomNavController = bottomNavController,
                                viewModel = mainViewModel,
                                sharedPreferences = sharedPreferences
                            )
                        }
                        is UiState.Error ->
                            Box(modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center) {
                                Column {
                                    Text(state.message, color = Red, fontWeight = FontWeight.Bold)
                                    //Text((uiState as UiState.Error<*>).message, color = Red, fontWeight = FontWeight.Bold)
                                }
                            }
                    }
                }
            }
        }
    }
}
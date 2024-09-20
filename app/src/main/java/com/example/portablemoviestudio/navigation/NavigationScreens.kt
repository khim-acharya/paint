package com.example.portablemoviestudio.navigation

enum class Screen {
    HOMESCREEN,
    DETAILSCREEN,
    FEBSCREEN,
}
sealed class MainScreenNavigationItems(val route: String) {
    object Home: MainScreenNavigationItems(Screen.HOMESCREEN.name)
    object Detail: MainScreenNavigationItems(Screen.DETAILSCREEN.name)
}
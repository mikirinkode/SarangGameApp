package com.mikirinkode.saranggame.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("home/{gameId}"){
        fun createRoute(gameId: Int) = "home/$gameId"
    }
    object About: Screen("home/about")
}
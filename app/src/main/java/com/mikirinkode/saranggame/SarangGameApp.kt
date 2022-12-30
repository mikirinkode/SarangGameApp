package com.mikirinkode.saranggame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mikirinkode.saranggame.ui.navigation.Screen
import com.mikirinkode.saranggame.ui.screen.AboutScreen
import com.mikirinkode.saranggame.ui.screen.DetailScreen
import com.mikirinkode.saranggame.ui.screen.HomeScreen


@Composable
fun SarangGameApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToAbout = {
                    navController.navigate(Screen.About.route)
                },
                navigateToDetail = { gameId ->
                    navController.navigate(Screen.GameDetail.createRoute(gameId))
                })
        }

        // route: home/{gameId}
        composable(
            Screen.GameDetail.route,
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType },
            )
        ) {
            val gameId = it.arguments?.getInt("gameId") ?: 0
            DetailScreen()
        }

        // route: home/about
        composable(Screen.About.route){
            AboutScreen()
        }
    }

}

@Preview
@Composable
fun SarangGameAppPreview() {

}
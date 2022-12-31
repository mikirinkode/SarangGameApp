package com.mikirinkode.saranggame

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                    navController.navigate(Screen.Detail.createRoute(gameId))
                })
        }

        // route: home/{gameId}
        composable(
            Screen.Detail.route,
            arguments = listOf(
                navArgument("gameId") { type = NavType.IntType },
            )
        ) {
            val gameId = it.arguments?.getInt("gameId") ?: 0
            val context = LocalContext.current

            DetailScreen(
                gameId = gameId,
                onShareClick = { title ->
                    shareGame(context, title)
                },
                openWebsite = { url ->
                    openWebsite(context, url)
                },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        // route: home/about
        composable(Screen.About.route) {
            AboutScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }

}

fun shareGame(context: Context, title: String) {
    val shareIntent = Intent()
    val appName = context.getString(R.string.app_name)
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey i found a cool game, $title on $appName")
    shareIntent.type = "text/plain"
    context.startActivity(
        Intent.createChooser(
            shareIntent,
            "Share To"
        )
    )
}

fun openWebsite(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(
        Intent.createChooser(
            intent, "Open Website"
        )
    )
}

@Preview
@Composable
fun SarangGameAppPreview() {

}
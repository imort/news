package com.github.imort.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.imort.news.ui.article.ArticleScreen
import com.github.imort.news.ui.news.NewsScreen

@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = "news", modifier = modifier) {
        composable("news") {
            NewsScreen(
                onButtonClick = { navController.navigateToArticle("id") }
            )
        }
        composable(
            route = "news/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            ),
        ) {
            ArticleScreen()
        }
    }
}

fun NavController.navigateToArticle(id: String) = navigate("news/$id")

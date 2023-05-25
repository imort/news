package com.github.imort.news

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.imort.news.data.Source

@Composable
internal fun AppNavHost(
    source: Source,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = "news", modifier = modifier) {
        composable("news") {
            NewsScreen(
                source = source,
                onButtonClick = { navController.navigate("news/sample") }
            )
        }
        composable(
            route = "news/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
            ),
        ) { entry ->
            DetailsScreen(
                id = entry.arguments?.getString("id") ?: error("Missing id"),
            )
        }
    }
}

@Composable
fun NewsScreen(
    source: Source,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = "News from ${source.country}!",
        )
        Button(onClick = onButtonClick) {
            Text(text = "Go")
        }
    }
}

@Composable
fun DetailsScreen(id: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $id Details!",
        modifier = modifier
    )
}

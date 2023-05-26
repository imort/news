package com.github.imort.news.ui.article

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleViewModel = hiltViewModel(),
) {
    Text(
        text = "Hello ${viewModel.id} Details!",
        modifier = modifier
    )
}
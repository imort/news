package com.github.imort.news.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    onButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = "News from ${viewModel.country}!",
        )
        Button(onClick = onButtonClick) {
            Text(text = "Go")
        }
    }
}
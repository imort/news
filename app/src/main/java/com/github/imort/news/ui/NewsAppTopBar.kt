package com.github.imort.news.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppTopBar(title: String) {
    TopAppBar(
        title = { Text(text = title) },
    )
}

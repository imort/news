package com.github.imort.news.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.imort.news.data.Article
import com.github.imort.news.navigateToArticle
import com.github.imort.news.ui.ArticleImage
import com.github.imort.news.ui.Error
import com.github.imort.news.ui.MviEffectCollector
import com.github.imort.news.ui.NewsAppTopBar
import com.github.imort.news.ui.Progress
import com.github.imort.news.ui.news.NewsContract.Effect
import com.github.imort.news.ui.news.NewsContract.Event
import com.github.imort.news.ui.news.NewsContract.State
import com.github.imort.news.ui.theme.NewsTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun NewsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    News(
        state = viewModel.viewState.value,
        effects = viewModel.effects,
        onEvent = viewModel::emit,
        onEffect = {
            when (it) {
                is Effect.Select -> navController.navigateToArticle(it.article.id)
            }
        },
        modifier = modifier,
    )
}

@Composable
internal fun News(
    state: State,
    effects: Flow<Effect>,
    onEvent: (Event) -> Unit,
    onEffect: (Effect) -> Unit,
    modifier: Modifier = Modifier,
) {
    MviEffectCollector(flow = effects, handler = onEffect)
    Scaffold(
        modifier = modifier,
        topBar = { NewsAppTopBar(title = "News from ${state.country.uppercase()}") },
    ) { paddingValues ->
        val mod = Modifier.padding(paddingValues)
        when {
            state.loading -> Progress(mod)
            state.error -> Error(mod, "retry") { onEvent(Event.Retry) }
            else -> ArticleGrid(state.articles, mod) {
                onEvent(Event.Select(it))
            }
        }
    }
}

@Composable
fun ArticleGrid(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 400.dp), modifier = modifier) {
        items(items = articles, key = { it.id }) { article ->
            ArticleItem(article) { onArticleClick(article) }
        }
    }
}

@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArticleImage(article.urlToImage, 120.dp)
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = article.author ?: "Unknown author",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = article.title ?: "Unknown title",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    NewsTheme {
        ArticleItem(article = Article.preview)
    }
}

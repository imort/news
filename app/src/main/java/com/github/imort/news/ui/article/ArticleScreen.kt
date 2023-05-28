package com.github.imort.news.ui.article

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.imort.news.data.Article
import com.github.imort.news.navigateToHome
import com.github.imort.news.ui.ArticleImage
import com.github.imort.news.ui.Error
import com.github.imort.news.ui.MviEffectCollector
import com.github.imort.news.ui.NewsAppTopBar
import com.github.imort.news.ui.article.ArticleContract.Effect
import com.github.imort.news.ui.article.ArticleContract.Event
import com.github.imort.news.ui.article.ArticleContract.State
import kotlinx.coroutines.flow.Flow

@Composable
fun ArticleScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ArticleViewModel = hiltViewModel(),
) {
    Article(
        state = viewModel.viewState.value,
        effects = viewModel.effects,
        onEvent = viewModel::emit,
        onEffect = {
            when (it) {
                is Effect.Return -> navController.navigateToHome()
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun Article(
    state: State,
    effects: Flow<Effect>,
    onEvent: (Event) -> Unit,
    onEffect: (Effect) -> Unit,
    modifier: Modifier = Modifier,
) {
    MviEffectCollector(flow = effects, handler = onEffect)

    val article = state.article
    val title = remember {
        buildString {
            append("Article")
            article?.author?.also {
                append(" by $it")
            }
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = { NewsAppTopBar(title = title) },
    ) { paddingValues ->
        val mod = Modifier.padding(paddingValues)
        when (article) {
            null -> Error(mod, "return") { onEvent(Event.Return) }
            else -> ArticleContent(article, mod)
        }
    }
}

@Composable
private fun ArticleContent(article: Article, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        if (maxWidth < maxHeight) {
            ArticlePortrait(article, maxWidth)
        } else {
            ArticleLandscape(article, maxHeight)
        }
    }
}

@Composable
private fun ArticlePortrait(article: Article, minDimension: Dp, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.scrollable(
            state = rememberScrollableState { it },
            orientation = Orientation.Vertical,
        ),
    ) {
        ArticleImage(article.urlToImage, minDimension)
        ArticleBody(article)
    }
}

@Composable
private fun ArticleLandscape(article: Article, minDimension: Dp, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ArticleImage(article.urlToImage, minDimension)
        Column(
            modifier = modifier.scrollable(
                state = rememberScrollableState { it },
                orientation = Orientation.Vertical,
            )
        ) {
            ArticleBody(article)
        }
    }
}

@Composable
private fun ArticleBody(article: Article) {
    Spacer(modifier = Modifier.size(8.dp))
    article.title?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
    article.description?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
    article.content?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleContentPreview() {
    ArticleContent(Article.preview)
}

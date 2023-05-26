package com.github.imort.news.ui.news

import androidx.lifecycle.ViewModel
import com.github.imort.news.data.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    source: Source,
) : ViewModel() {
    val country = source.country
}
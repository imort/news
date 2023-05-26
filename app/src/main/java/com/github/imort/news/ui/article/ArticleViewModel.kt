package com.github.imort.news.ui.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val id: String = savedStateHandle["id"] ?: error("Missing id")
}
package com.example.news_compose_c40.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_compose_c40.api.NewsService
import com.example.news_compose_c40.model.article.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val webService: NewsService) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: String get() = _searchQuery.value

    private var _articlesList = mutableStateOf<List<Article>?>(null)
    val articlesList: List<Article>? get() = _articlesList.value

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = webService.getArticles(_searchQuery.value).articles
            _articlesList.value = articles
        }
    }
}

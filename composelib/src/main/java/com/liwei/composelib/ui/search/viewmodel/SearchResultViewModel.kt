package com.liwei.composelib.ui.search.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.liwei.composelib.api.Api
import com.liwei.composelib.api.apiCall
import com.liwei.composelib.ui.home.model.Article
import com.liwei.composelib.ui.mine.viewmodel.CollectViewModel
import com.liwei.composelib.widget.LoadState
import com.liwei.composelib.widget.Toaster

class SearchResultViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Article>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var keyword = ""
    private var page = 0

    fun setKeyword(keyword: String) {
        if (this.keyword.isEmpty()) {
            this.keyword = keyword
            firstLoad()
        }
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().search(page, keyword) }
            if (articleList.isSuccess()) {
                pageState = LoadState.SUCCESS
                if (articleList.data!!.datas.isNotEmpty()) {
                    list = articleList.data!!.datas
                } else {
                    pageState = LoadState.EMPTY
                }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshingState = true
            val articleList = apiCall { Api.get().search(page, keyword) }
            if (articleList.isSuccess()) {
                list = articleList.data!!.datas
                refreshingState = false
            } else {
                refreshingState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun onLoad() {
        viewModelScope.launch {
            loadState = true
            val articleList = apiCall { Api.get().search(page + 1, keyword) }
            if (articleList.isSuccess()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas)
                }
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun collect(article: Article) {
        viewModelScope.launch {
            showLoading = true
            CollectViewModel.collect(article)
            showLoading = false
        }
    }
}
package com.liwei.composelib.ui.mine.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.liwei.composelib.api.Api
import com.liwei.composelib.api.apiCall
import com.liwei.composelib.ui.home.model.Article
import com.liwei.composelib.widget.LoadState
import com.liwei.composelib.widget.Toaster

/**
 * Created by wcy on 2021/4/1.
 */
class CollectViewModel : ViewModel() {
    var pageState by mutableStateOf(LoadState.LOADING)
    var showLoading by mutableStateOf(false)
    var list by mutableStateOf(listOf<Article>())
    var refreshingState by mutableStateOf(false)
    var loadState by mutableStateOf(false)
    private var page = 0

    init {
        firstLoad()
    }

    fun firstLoad() {
        viewModelScope.launch {
            page = 0
            pageState = LoadState.LOADING
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccess()) {
                pageState = LoadState.SUCCESS
                list = articleList.data!!.datas.onEach { it.collect = true }
            } else {
                pageState = LoadState.FAIL
            }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            page = 0
            refreshingState = true
            val articleList = apiCall { Api.get().getCollectArticleList() }
            if (articleList.isSuccess()) {
                list = articleList.data!!.datas.onEach { it.collect = true }
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
            val articleList = apiCall { Api.get().getCollectArticleList(page + 1) }
            if (articleList.isSuccess()) {
                page++
                list = list.toMutableList().apply {
                    addAll(articleList.data!!.datas.onEach { it.collect = true })
                }
                loadState = false
            } else {
                loadState = false
                Toaster.show("加载失败")
            }
        }
    }

    fun uncollect(article: Article) {
        viewModelScope.launch {
            showLoading = true
            val res = collect(article) { it.originId }
            if (res) {
                onRefresh()
            }
            showLoading = false
        }
    }

    companion object {
        suspend fun collect(
            article: Article,
            getId: (article: Article) -> Long = { it.id }
        ): Boolean {
            val id = getId.invoke(article)
            if (article.collect) {
                val res = apiCall { Api.get().uncollect(id) }
                if (res.isSuccessIgnoreData()) {
                    article.collect = false
                    return true
                } else {
                    Toaster.show(res.msg)
                }
            } else {
                val res = apiCall { Api.get().collect(id) }
                if (res.isSuccessIgnoreData()) {
                    article.collect = true
                    return true
                } else {
                    Toaster.show(res.msg)
                }
            }
            return false
        }
    }
}
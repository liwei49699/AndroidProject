package com.liwei.composelib.ui.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.liwei.composelib.theme.Colors
import com.liwei.composelib.ui.home.ArticleItem
import com.liwei.composelib.ui.mine.viewmodel.CollectViewModel
import com.liwei.composelib.widget.PageLoading
import com.liwei.composelib.widget.SwipeRefreshAndLoadLayout
import com.liwei.composelib.widget.TitleBar

@Composable
fun CollectList(navController: NavHostController) {
    val viewModel: CollectViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleBar(title = "ζηζΆθ", onBack = { navController.popBackStack() })
        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.firstLoad() }) {
            SwipeRefreshAndLoadLayout(
                refreshingState = viewModel.refreshingState,
                loadState = viewModel.loadState,
                onRefresh = { viewModel.onRefresh() },
                onLoad = { viewModel.onLoad() }) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Colors.white)) {
                    itemsIndexed(viewModel.list) { index, item ->
                        ArticleItem(navController, item) {
                            viewModel.uncollect(item)
                        }
                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}
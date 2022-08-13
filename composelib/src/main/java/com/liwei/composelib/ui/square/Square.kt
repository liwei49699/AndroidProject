package com.liwei.composelib.ui.square

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.liwei.composelib.R
import com.liwei.composelib.theme.Colors
import com.liwei.composelib.ui.home.ArticleItem
import com.liwei.composelib.ui.square.viewmodel.SquareViewModel
import com.liwei.composelib.widget.PageLoading
import com.liwei.composelib.widget.SwipeRefreshAndLoadLayout
import com.liwei.composelib.widget.TitleBar
import com.liwei.composelib.widget.Toaster

/**
 * Created by wcy on 2021/3/31.
 */

@Composable
@Preview
fun Square(navController: NavHostController = rememberNavController()) {
    val viewModel: SquareViewModel = viewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TitleBar(
            title = "广场",
            icon = R.drawable.ic_share,
            onIconClick = {
                Toaster.show("分享")
            }
        )
        PageLoading(
            loadState = viewModel.pageState,
            onReload = { viewModel.firstLoad() },
            showLoading = viewModel.showLoading
        ) {
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
                            viewModel.collect(item)
                        }
                        Divider(Modifier.padding(16.dp, 0.dp), thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}
package com.liwei.composelib.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.liwei.composelib.ui.home.Home
import com.liwei.composelib.ui.mine.Mine
import com.liwei.composelib.ui.square.Square
import com.liwei.composelib.ui.wechat.WeChat
import com.liwei.composelib.widget.BottomTab

@ExperimentalPagerApi
@Composable
@Preview
fun Main(navController: NavHostController = rememberNavController()) {
    Column(Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 3)
        HorizontalPager(pagerState, Modifier.weight(1f)) { page ->
            when (page) {
                0 -> {
                    Home(navController)
                }
                1 -> {
                    Square(navController)
                }
                2 -> {
                    WeChat(navController)
                }
                3 -> {
                    Mine(navController)
                }
            }
        }
        BottomTab(pagerState.currentPage) {
            scope.launch {
                pagerState.scrollToPage(it)
            }
        }
    }
}
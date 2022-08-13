package com.liwei.composelib.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.liwei.composelib.ui.Main
import com.liwei.composelib.ui.mine.CollectList
import com.liwei.composelib.ui.mine.Login
import com.liwei.composelib.ui.mine.Register
import com.liwei.composelib.ui.search.Search
import com.liwei.composelib.ui.search.SearchResult
import com.liwei.composelib.ui.web.Web

@ExperimentalPagerApi
@Composable
@Preview
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { Main(navController) }
        composable("web?url={url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            Web(navController, url)
        }
        composable("login") { Login(navController) }
        composable("register") { Register(navController) }
        composable("collect") { CollectList(navController) }
        composable("search") { Search(navController) }
        composable("search_result?keyword={keyword}") { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            SearchResult(navController, keyword)
        }
    }
}
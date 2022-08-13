package com.liwei.composelib.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.liwei.composelib.theme.WanandroidTheme
import com.liwei.composelib.widget.ComposeNavigation

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanandroidTheme {
                ComposeNavigation()
            }
        }
    }
}
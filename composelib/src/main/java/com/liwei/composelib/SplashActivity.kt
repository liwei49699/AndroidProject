package com.liwei.composelib

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.liwei.composelib.ui.theme.AndroidProjectTheme
import kotlinx.coroutines.delay

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/16 4:15 下午
 * @Version:        1.0
 */
// android 不应该提供启动界面
@SuppressLint("CustomSplashScreen")
class SplashActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Splash {
                        Toast.makeText(this, "test", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, ComposeActivity::class.java))
                    }
                }
            }
        }
    }
}
@Composable
fun Splash(offsetState: Dp, alphaState: Float ){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(modifier = Modifier
                .size(100.dp)
                .alpha(alpha = alphaState),
                painter = painterResource(id = getLogo()),
                contentDescription = stringResource(id = R.string.app_name))
        }
        Text(modifier = Modifier
            .offset(y = offsetState)
            .alpha(alpha = alphaState),
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            maxLines = 1)
    }
}

private fun getLogo(): Int {
    return R.mipmap.ic_launcher
}

@Composable
fun Splash(
    gotoHomeScreen: () -> Unit
) {
    // 1
    var start by remember { mutableStateOf(false) }
    // 2
    val offset by animateDpAsState(
        targetValue = if (start) 0.dp else 100.dp,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alpha by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    // 3
    LaunchedEffect(key1 = Unit) {
        start = true
        delay(2000L)
        gotoHomeScreen()
    }

    Splash(offsetState = offset, alphaState = alpha)
}
package com.liwei.androidproject.web

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.liwei.androidproject.R

class WebLauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_launcher)

        if (intent.action.equals(Intent.ACTION_VIEW)) {
            //通过浏览器打开
            val data = intent.data
            val pid = data?.getQueryParameter("pid") ?: ""
            LogUtils.d("WebLauncherActivity", "onCreate: pid-$pid")
        }
    }
}
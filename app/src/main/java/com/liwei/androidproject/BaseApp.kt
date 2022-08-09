package com.liwei.androidproject

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.blankj.utilcode.util.LogUtils
import kotlin.properties.Delegates

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 10:46 上午
 * @Version:        1.0
 */
class BaseApp: Application() {

    companion object {
        var mApp by Delegates.notNull<Application>()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mApp = this

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    class AppLifecycleObserver: LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onForegroundo() {
            LogUtils.d("BaseApp", "onForeground: 应用创建")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onForeground() {
            LogUtils.d("BaseApp", "onForeground: 应用回到前台")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onForeground1() {
            LogUtils.d("BaseApp", "onForeground: 应用回到前台1")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onBackground() {
            LogUtils.d("BaseApp", "onBackground: 应用退到后台")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun onBackground1() {
            LogUtils.d("BaseApp", "onBackground: 应用退到后台1")
        }
    }
}
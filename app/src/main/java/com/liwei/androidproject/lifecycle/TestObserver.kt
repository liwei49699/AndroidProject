package com.liwei.androidproject.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.blankj.utilcode.util.LogUtils
import com.liwei.androidproject.ktx.cTAG

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 6:37 下午
 * @Version:        1.0
 */
class TestObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        LogUtils.d("TestObserver $cTAG", "onCreate:  ")
    }
}
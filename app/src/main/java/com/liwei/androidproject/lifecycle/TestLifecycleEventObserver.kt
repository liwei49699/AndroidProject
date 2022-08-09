package com.liwei.androidproject.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 6:48 下午
 * @Version:        1.0
 */
class TestLifecycleEventObserver: LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        LogUtils.d("TestLifecycleEventObserver ${source.javaClass.canonicalName}", "onStateChanged: ${event.name}" )
    }

}
package com.liwei.androidproject.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 6:45 下午
 * @Version:        1.0
 */
class TestLifecycleObserver: DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        LogUtils.d("TestLifecycleObserver", "onCreate: ")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtils.d("TestLifecycleObserver", "onStart: ")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        LogUtils.d("TestLifecycleObserver", "onResume: ")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        LogUtils.d("TestLifecycleObserver", "onPause: ")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtils.d("TestLifecycleObserver", "onStop: ")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        LogUtils.d("TestLifecycleObserver", "onDestroy: ")
    }
}
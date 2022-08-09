package com.liwei.androidproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils

/**
 * @Description:    AndroidViewModel提供context对象 且是全局防止内存泄漏
 * @Author:         apple
 * @CreateDate:     2022/6/14 9:47 上午
 * @Version:        1.0
 */
class TestAndroidViewModel(var applicationCurrent: Application): AndroidViewModel(applicationCurrent) {

    var _testLiveData: MutableLiveData<String> = MutableLiveData("abc")
    var mTestLiveData: LiveData<String> = _testLiveData

    fun setValue(value: String) {
        LogUtils.d("TestAndroidViewModel $applicationCurrent", "setValue: " + getApplication())
        _testLiveData.value = value
    }
}
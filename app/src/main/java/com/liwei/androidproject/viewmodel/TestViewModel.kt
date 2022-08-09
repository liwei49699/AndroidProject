package com.liwei.androidproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/13 5:52 下午
 * @Version:        1.0
 */
class TestViewModel: ViewModel() {

    var _testLiveData: MutableLiveData<String> = MutableLiveData("abc")
    var mTestLiveData: LiveData<String> = _testLiveData

    fun setValue(value: String) {
        _testLiveData.value = value
        viewModelScope
    }

    // viewModelStore 存储

    /**
     * 由于屏幕旋转导致的Activity重建，该方法不会被调用
     * 只有ViewModel已经没有任何Activity与之有关联，系统则会调用该方法，你可以在此清理资源
     */
    override fun onCleared() {
        super.onCleared()
    }
}
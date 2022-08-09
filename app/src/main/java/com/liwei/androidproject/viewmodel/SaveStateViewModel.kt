package com.liwei.androidproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/16 2:44 下午
 * @Version:        1.0
 */
class SaveStateViewModel(savedStateHandle: SavedStateHandle): ViewModel() {

    val nameLiveData = savedStateHandle.getLiveData<String>(KEY_NAME)
    val blogLiveData = MutableLiveData<String>()

    companion object {
        private const val KEY_NAME = "keyName"
    }
}
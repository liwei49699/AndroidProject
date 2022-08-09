package com.liwei.androidproject.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liwei.androidproject.viewmodel.BaseFragment

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 3:33 下午
 * @Version:        1.0
 */
class LifecycleFragment: BaseFragment() {

    var mLiveData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(TestLifecycleEventObserver())

        mLiveData.observeForever {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val textView = TextView(requireContext())
        textView.text = "LifecycleFragment"
        return textView
    }
}
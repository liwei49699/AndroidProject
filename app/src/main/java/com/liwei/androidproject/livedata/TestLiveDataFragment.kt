package com.liwei.androidproject.livedata

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.liwei.androidproject.viewmodel.BaseFragment

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/16 10:28 上午
 * @Version:        1.0
 */
class TestLiveDataFragment: BaseFragment() {

    private val mTestLiveData = MediatorLiveData<String>()
    private val mLengthLiveData = MediatorLiveData<Int>()
    private val mBooleanLiveData = MutableLiveData<Boolean>()
    private val mBooleanLiveData2 = MutableLiveData<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val textView = TextView(requireContext())
        textView.text = "LiveDataFragment"
        return textView
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lifecycle.State.START 才会回调 包含onStart onResume onPause
        mTestLiveData.observe(viewLifecycleOwner) {
            LogUtils.d("TestLiveDataFragment", "onViewCreated: $it")
        }

//        mTestLiveData.addSource(mBooleanLiveData2)
        mTestLiveData.addSource(mLengthLiveData) {
            LogUtils.d("TestLiveDataFragment", "onViewCreated: $it")
        }

        mLengthLiveData.addSource(mBooleanLiveData2) {

        }

        val observer = Observer<Boolean> {

        }

//        mLengthLiveData.addSource(mBooleanLiveData, observer)
//        mLengthLiveData.addSource(mBooleanLiveData2, observer)

        mLengthLiveData.addSource(mBooleanLiveData) {
            LogUtils.d("TestLiveDataFragment", "onViewCreated: $it")
        }

        mBooleanLiveData.observe(viewLifecycleOwner) {
            LogUtils.d("TestLiveDataFragment", "onViewCreated: $it")
        }

        mBooleanLiveData.observe(viewLifecycleOwner) {

        }

        mBooleanLiveData.value = true

        // map boolean -> Int
        val map: LiveData<Int> = Transformations.map(mBooleanLiveData) {
            3
        }

        val liveData = MutableLiveData<String>()
        val switchMap: LiveData<String> = Transformations.switchMap(mBooleanLiveData) {
            liveData
        }

        switchMap.observe(viewLifecycleOwner) {

        }

        val myCallLiveData = MyCallLiveData()
        myCallLiveData.liveData.observe(viewLifecycleOwner) {

            LogUtils.d("TestLiveDataFragment", "onViewCreated:111 $it")
        }
    }
}

@SuppressLint("RestrictedApi")
class MyCallLiveData(): ComputableLiveData<Boolean>() {

    override fun compute(): Boolean {
        LogUtils.d("TestLiveDataFragment", "compute: ${Thread.currentThread().name}")
        return false
    }

}
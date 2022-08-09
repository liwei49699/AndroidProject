package com.liwei.androidproject.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.LogUtils
import com.liwei.androidproject.BaseApp

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/13 6:33 下午
 * @Version:        1.0
 */
class ViewModelFragment: BaseFragment() {

    private val mTestViewModel by activityViewModels<TestViewModel>()
    private val testAndroidViewModel by viewModels<TestAndroidViewModel>() {
        ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.mApp)
    }
//    val testAndroidViewModel2 by lazy {
//        ViewModelProvider(REQ()!!).get(TestAndroidViewModel::class.java)
//    }

    private val saveStateViewModel by viewModels<SaveStateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val textView = TextView(requireContext())
        textView.text = "viewModelFragment"
        return textView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testViewModel1 = ViewModelProviders.of(requireActivity()).get(TestViewModel::class.java)

        val value = testAndroidViewModel.mTestLiveData.value
        LogUtils.d("ViewModelFragment $testAndroidViewModel", "initViewModel: TestAndroidViewModel $value")

        val testAndroidViewModel2 = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.mApp)).get(TestAndroidViewModel::class.java)
        val value12 = testAndroidViewModel2.mTestLiveData.value
        LogUtils.d("ViewModelFragment$testAndroidViewModel2", "initViewModel:TestAndroidViewModel $value12")

        LogUtils.d("ViewModelFragment$mTestViewModel", "initViewModel:mTestViewModel ${mTestViewModel.mTestLiveData.value}")

        testAndroidViewModel2.mTestLiveData.observe(viewLifecycleOwner) {
            LogUtils.d("ViewModelFragment", "onViewCreated: $it")
        }

        view.setOnClickListener {
            testAndroidViewModel2._testLiveData.value = "121"
        }
    }
}
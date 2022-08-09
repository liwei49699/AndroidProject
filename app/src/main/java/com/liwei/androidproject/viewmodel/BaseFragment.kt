package com.liwei.androidproject.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/6/14 2:34 下午
 * @Version:        1.0
 */
open class BaseFragment: Fragment() {

    private lateinit var mContext: Context
    private val cTAG
        get() = this.javaClass.simpleName + lifecycle.currentState


    override fun onAttach(context: Context) {
        LogUtils.d("BaseFragment: $cTAG", "onAttach: ")
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.d("BaseFragment: $cTAG", "onCreate: ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtils.d("BaseFragment: $cTAG", "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogUtils.d("BaseFragment: $cTAG", "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        LogUtils.d("BaseFragment: $cTAG", "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        LogUtils.d("BaseFragment: $cTAG", "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        LogUtils.d("BaseFragment: $cTAG", "onResume: ")
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        // shou hide
        LogUtils.d("BaseFragment:  $cTAG", "onHiddenChanged: hidden:$hidden")
        super.onHiddenChanged(hidden)
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        // viewPager
        super.setUserVisibleHint(isVisibleToUser)
        LogUtils.d("BaseFragment  $cTAG", "setUserVisibleHint: $isVisibleToUser")
    }

    override fun onPause() {
        LogUtils.d("BaseFragment: $cTAG", "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        LogUtils.d("BaseFragment: $cTAG", "onStop: ")
        super.onStop()
    }

    override fun onDestroyView() {
        LogUtils.d("BaseFragment: $cTAG", "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onDestroy() {
        LogUtils.d("BaseFragment: $cTAG", "onDestroy: ")
        super.onDestroy()
    }

    override fun onDetach() {
        LogUtils.d("BaseFragment :$cTAG", "onDetach: ")
        super.onDetach()
    }
}
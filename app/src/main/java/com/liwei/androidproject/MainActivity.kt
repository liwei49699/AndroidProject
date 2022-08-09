package com.liwei.androidproject

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.liwei.androidproject.databinding.ActivityMainBinding
import com.liwei.androidproject.databinding.TitleTopBarBinding
import com.liwei.androidproject.lifecycle.LifecycleFragment
import com.liwei.androidproject.lifecycle.TestLifecycleEventObserver
import com.liwei.androidproject.lifecycle.TestLifecycleObserver
import com.liwei.androidproject.lifecycle.TestObserver
import com.liwei.androidproject.livedata.TestLiveDataFragment
import com.liwei.androidproject.viewmodel.TestAndroidViewModel
import com.liwei.androidproject.viewmodel.TestViewModel
import com.liwei.androidproject.viewmodel.ViewModelFragment
import com.liwei.nativelib.NativeLib
import io.flutter.embedding.android.FlutterFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    val testViewModel4 by viewModels<TestViewModel>()
    val testAndroidViewModel by viewModels<TestAndroidViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.mApp)
    }
    private val mBooleanLiveData = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringFromJNI = NativeLib().stringFromJNI()
        val stringFromJNI1 = NativeLib().sayHello("android 你好-")
//        val stringFromJNI1 = DiyNativeLib().HelloWorld()
        LogUtils.d("MainActivity", "onCreate: " + stringFromJNI)
        LogUtils.d("MainActivity", "onCreate1: " + stringFromJNI1)
//        LogUtils.d("MainActivity1", "onCreate: " + stringFromJNI1)
        mBooleanLiveData.observe(this) {

        }

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        val seekbar = mBinding.seekBar0
        seekbar.setTextSize(21F) // 设置字体大小

        seekbar.setTextColor(Color.WHITE) // 颜色

//        seekbar.setMyPadding(10, 10, 10, 10) // 设置padding 调用setpadding会无效

//        seekbar.setImageOffset(0, 0) // 可以不设置

        //seekbar.setTextPadding(-5, 0);// 可以不设置
        //seekbar.setTextPadding(-5, 0);// 可以不设置
//        seekbar.setMarksOffsetLeftRight((8f.dp / 2F).toInt())

                seekbar.max = 10

        seekbar.addMark(2)
        seekbar.addMark(4)
        seekbar.addMark(5)
        seekbar.addMark(6)
        seekbar.addMark(8)


        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                Log.d("MainActivity", "onProgressChanged: " + progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        val bind = TitleTopBarBinding.bind(mBinding.root)
        bind.tvTitle.text = "231321123123"
//        setContentView(R.layout.activity_main)
//        mBinding.layoutBar.tvTitle.text = "213213132"

        mBinding.tvTest.setOnClickListener {
//            startActivity(FlutterActivity.createDefaultIntent(this))
            changeFragment()
            mBinding.webJumpApp.loadUrl("javascript:var uselessvar =document.activeElement.value='"+132+"';");

            val putString = NativeLib().putString("12312313");
            val string = NativeLib().getString()
            LogUtils.d("MainActivity" + putString + Thread.currentThread().name, "onCreate: " + string)
        }

        lifecycleScope

//        addFlutterFragment()
        initWebSetting()
//        initViewModel()
//        initLifecycle()
    }

    private fun initLifecycle() {

        lifecycle.addObserver(TestObserver())
        lifecycle.addObserver(TestLifecycleObserver())
        lifecycle.addObserver(TestLifecycleEventObserver())
    }

    private fun initViewModel() {

        // 方式一
        val testViewModel1 = ViewModelProviders.of(this).get(TestViewModel::class.java)
        val value1 = testViewModel1.mTestLiveData.value
        LogUtils.d("MainActivity", "initViewModel: $value1")

        // 方式二
        val testViewModel2 = ViewModelProvider(this).get(TestViewModel::class.java)
        val testViewModel5 = ViewModelProvider(this).get("", TestViewModel::class.java)
        testViewModel2.setValue("abcde")
        val value2 = testViewModel2.mTestLiveData.value
        LogUtils.d("MainActivity", "initViewModel: $value2")

        // 方式三
        val testViewModel3 = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TestViewModel::class.java)
        testViewModel3.setValue("abcdef")
        val value3 = testViewModel4.mTestLiveData.value
        LogUtils.d("MainActivity", "initViewModel: $value3")

        // 方式四
        testViewModel4.setValue("abcdefgh")
        val value4 = testViewModel4.mTestLiveData.value
        LogUtils.d("MainActivity $testViewModel4", "initViewModel: $value4")

        testAndroidViewModel.setValue("MainActivity")
        val value = testAndroidViewModel.mTestLiveData.value
        LogUtils.d("MainActivity" + testAndroidViewModel, "initViewModel:TestAndroidViewModel $value")

        val testAndroidViewModel2 = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApp.mApp)).get(TestAndroidViewModel::class.java)
        testAndroidViewModel2.setValue("MainActivity")
        val value12 = testAndroidViewModel2.mTestLiveData.value
        LogUtils.d("MainActivity" + testAndroidViewModel2, "initViewModel:TestAndroidViewModel $value12")

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSetting() {

        val wvLocal = mBinding.webJumpApp
        wvLocal.webChromeClient = WebChromeClient()

        val settings = wvLocal.settings
        settings.javaScriptEnabled = true
        wvLocal.settings.setSupportZoom(false)
        wvLocal.settings.javaScriptEnabled = true
        wvLocal.addJavascriptInterface(JavaScriptInterface(this), "android");
//        wvLocal.webViewClient = object : WebViewClient() {}
        wvLocal.loadUrl("file:///android_asset/test.html")
    }

    private var mChangeFragmentCount = 0

    private fun addFlutterFragment() {
        supportFragmentManager.beginTransaction().add(R.id.fcv_content, FlutterFragment.createDefault(), "flutter_fragment").commit()
    }

    inner class JavaScriptInterface(private val context: Context) {
        /**
         * 与js交互时用到的方法，在js里直接调用的
         */
        @JavascriptInterface
        fun showToast(ssss: String) {
            lifecycleScope.launch {
                Toast.makeText(context, ssss + Thread.currentThread().name, Toast.LENGTH_LONG).show();
                mBinding.webJumpApp.loadUrl("javascript:var uselessvar =document.activeElement.value='"+132+"';");
            }
        }
    }

    private val mViewModelFragment = ViewModelFragment()
    private val mTestLiveDataFragment = TestLiveDataFragment()
    private val mLifecycleFragment = LifecycleFragment()
    private val mFlutterFragment by lazy {
        FlutterFragment.createDefault()
    }

    private fun changeFragment() {

        when {
            mChangeFragmentCount % 4 == 1 -> {
                showFragment(mViewModelFragment)
            }
            mChangeFragmentCount % 4 == 2 -> {
                showFragment(mLifecycleFragment)
            }
            mChangeFragmentCount % 4 == 3 -> {
                showFragment(mTestLiveDataFragment)
            }
            else -> {
                showFragment(mFlutterFragment)
            }
        }
        mChangeFragmentCount ++
    }

    private var mCurrentFragment: Fragment? = null

    private fun showFragment(fragment: Fragment) {
        // replace替换
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fcv_content, fragment)
//        transaction.commit()

        // show切换
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (mCurrentFragment != null && mCurrentFragment != fragment) {
            if (fragment.isAdded) {
                beginTransaction.hide(mCurrentFragment!!).show(fragment)
                // Lifecycle.State.CREATED 回退 走 onPause onStop onDestroyView
                // Lifecycle.State.START 回退 走 onPause
                // Lifecycle.State.RESUMED 前进 走 onResume
                beginTransaction.setMaxLifecycle(mCurrentFragment!!, Lifecycle.State.STARTED)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                beginTransaction.commit()
            } else {
                beginTransaction.hide(mCurrentFragment!!).add(R.id.fcv_content, fragment)
                beginTransaction.setMaxLifecycle(mCurrentFragment!!, Lifecycle.State.STARTED)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                beginTransaction.commit()
            }
            mCurrentFragment = fragment
        } else if (mCurrentFragment == null) {
            if (fragment.isAdded) {
                beginTransaction.show(fragment)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                beginTransaction.commit()
            } else {
                beginTransaction.add(R.id.fcv_content, fragment)
                beginTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                beginTransaction.commit()
            }
            mCurrentFragment = fragment
        }
        //不会回调生命中周期方法 会回调onHiddenChanged
    }


    class LogInViewModelFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            try {
                return modelClass.newInstance() //使用newInstance反射实例ViewModel，并且传出去
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }
            throw RuntimeException()
        }
    }
}
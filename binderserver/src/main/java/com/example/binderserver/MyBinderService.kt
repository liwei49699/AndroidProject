package com.example.binderserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.binderlib.IMyAidlInterface

/**
 * @Description:    desc
 * @Author:         apple
 * @CreateDate:     2022/8/6 4:20 下午
 * @Version:        1.0
 */
class MyBinderService: Service() {
    override fun onBind(intent: Intent?): IBinder? {

        return MyBinder()
    }

    class MyBinder: IMyAidlInterface.Stub() {
        override fun add(a: Int, b: String?): String {
            Log.d("--TAG--", "binderServer")
            return b.plus(a)
        }
    }
}
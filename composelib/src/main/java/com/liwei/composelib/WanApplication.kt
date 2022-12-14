package com.liwei.composelib

import android.app.Application
import android.content.Context
import android.util.Log
import me.wcy.mockhttp.MockHttp
import me.wcy.mockhttp.MockHttpOptions
import com.liwei.composelib.auth.AuthManager

class WanApplication : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AuthManager.init()
        val options = MockHttpOptions.Builder()
            .setMockServerPort(3000)
            .setMockSleepTime(500)
            .setLogEnable(true)
            .setLogLevel(Log.ERROR)
            .build()
        MockHttp.get().setMockHttpOptions(options)
        MockHttp.get().start(this)
    }
}
package com.sw.debug.example

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.sw.debug.view.DebugViewWrapper
import com.squareup.leakcanary.LeakCanary
import com.sw.debug.view.modules.TimerModule

@SuppressLint("StaticFieldLeak")
var application: Application? = null

class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
        TimerModule.instance.begin(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)


        initDebugView()

    }

    private fun initDebugView() {
        DebugViewWrapper.instance.init(
                DebugViewWrapper.Builder(this)
                        .viewWidth(250) /* the width of debug-view */
                        .bgColor(0x6f677700) /* the color of debug-view */
                        .alwaysShowOverlaySetting(true) /* the flag for always showing Overlay Setting */
                        .logMaxLines(20) /* the max lines of log */
        )


        DebugViewWrapper.instance.show()
    }
}

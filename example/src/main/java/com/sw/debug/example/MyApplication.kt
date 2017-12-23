package com.sw.debug.example

import android.app.Application
import android.content.Context

import com.sw.debug.view.DebugViewWrapper
import com.squareup.leakcanary.LeakCanary


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        /*
        * init in other thread
        * */
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                initDebugView();
        //            }
        //        },"debug-view").start();

        /*
        * init in ui thread
        * */
        initDebugView()

    }

    private fun initDebugView() {
        DebugViewWrapper.instance.init(
                DebugViewWrapper.Builder(this)
                        .viewWidth(250)
                        .bgColor(0x5f670000)
                        .alwaysShowOverlaySetting(true)
                        .logMaxLines(20)
        )
        DebugViewWrapper.instance.show()

//        val preferences = getSharedPreferences("debug_view", Context.MODE_PRIVATE)
//        if (preferences.getBoolean("debug_view_enable", false)) {
//            DebugViewWrapper.getInstance().show()
//        }
    }
}

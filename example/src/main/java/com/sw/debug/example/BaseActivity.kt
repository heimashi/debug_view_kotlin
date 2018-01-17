package com.sw.debug.example

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.sw.debug.view.modules.TimerModule

@SuppressLint("Registered")
open class BaseActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TimerModule.instance.begin(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            TimerModule.instance.end(this)
        }
    }
}
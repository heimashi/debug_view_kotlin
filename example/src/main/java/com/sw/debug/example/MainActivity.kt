package com.sw.debug.example

import android.app.Activity
import android.os.Bundle

import com.sw.debug.view.modules.LogModule
import kotlinx.android.synthetic.main.activity_example.*

fun log(msg: String) {
    LogModule.instance.log(msg)
}

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        add_log_btn.setOnClickListener {
            log("click:" + System.currentTimeMillis())
        }

        log("Main onCreate")

    }

    override fun onResume() {
        super.onResume()
        log("Main onResume")
    }

    override fun onPause() {
        super.onPause()
        log("Main onPause")
    }
}

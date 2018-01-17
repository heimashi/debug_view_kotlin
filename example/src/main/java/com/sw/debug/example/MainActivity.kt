package com.sw.debug.example

import android.os.Bundle

import com.sw.debug.view.modules.LogModule
import com.sw.debug.view.modules.TimerModule
import kotlinx.android.synthetic.main.activity_example.*
import java.text.SimpleDateFormat
import java.util.*

fun log(msg: String) {
    LogModule.instance.log(msg)
}

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        add_log_btn.setOnClickListener {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:MM:ss", Locale.CHINA)
            log("ADD_LOG::Click at:" + dateFormat.format(Date()))
        }

        log("Main onCreate")

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){
            TimerModule.instance.end(application)
        }
        log("Main onWindowFocusChanged")
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

package com.sw.debug.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

import com.sw.debug.view.modules.LogModule



class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        val button = Button(this)
        button.text = "Click to add log into DebugView"
        linearLayout.addView(button)
        val layoutParams = button.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin = 200
        val button2 = Button(this)
        button2.text = "Click to SettingActivity"
        linearLayout.addView(button2)

        setContentView(linearLayout)
        button.setOnClickListener {
            Thread(Runnable {
                LogModule.instance.log("click:" + System.currentTimeMillis());
            }).start()
        }

        button2.setOnClickListener {
            LogModule.instance.log("to SettingActivity ::::::::::::" + System.currentTimeMillis())
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }
    }


}

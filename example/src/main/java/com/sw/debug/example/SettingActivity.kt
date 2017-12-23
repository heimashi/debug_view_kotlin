package com.sw.debug.example

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.sw.debug.view.DebugViewWrapper


class SettingActivity : Activity() {

    private var button: Button? = null

    val debugViewEnable: Boolean
        get() {
            val preferences = getSharedPreferences("debug_view", Context.MODE_PRIVATE)
            return preferences.getBoolean("debug_view_enable", false)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = Button(this)
        setContentView(button)
        updateBtn()
        button!!.setOnClickListener {
            if (debugViewEnable) {
                saveDebugViewEnable(false)
                DebugViewWrapper.instance.hide()
            } else {
                saveDebugViewEnable(true)
                DebugViewWrapper.instance.show()
            }
            updateBtn()
        }
    }

    private fun updateBtn() {
        if (debugViewEnable) {
            button!!.text = "已开始DebugView，点击关闭"
        } else {
            button!!.text = "已关闭DebugView，点击开启"
        }
    }


    fun saveDebugViewEnable(enable: Boolean): Boolean {
        val preferences = getSharedPreferences("debug_view", Context.MODE_PRIVATE)
        return preferences.edit().putBoolean("debug_view_enable", enable).commit()
    }

}

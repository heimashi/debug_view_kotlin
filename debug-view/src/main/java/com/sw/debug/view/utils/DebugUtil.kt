package com.sw.debug.view.utils


import android.content.Context
import android.util.Log

object DebugUtil {

    fun log(msg: String) {
        Log.d("DEBUG_VIEW", msg)
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}

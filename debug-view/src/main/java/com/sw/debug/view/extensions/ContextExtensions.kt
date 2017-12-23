package com.sw.debug.view.extensions

import android.content.Context
import android.util.Log


fun Context.dip2px(dpValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun log(msg: String) {
    Log.d("DEBUG_VIEW", msg)
}


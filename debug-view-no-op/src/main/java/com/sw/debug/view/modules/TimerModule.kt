@file:JvmName("TimerModule")

package com.sw.debug.view.modules

import com.sw.debug.view.base.AbstractDebugModule


class TimerModule private constructor() : AbstractDebugModule<List<String>>(null, null) {

    fun setLogMaxLines(maxLines: Int) {
    }

    fun begin(obj: Any) {
    }

    fun end(obj: Any) {
    }

    companion object {

        val instance: TimerModule
            get() = TimerModule()
    }


}

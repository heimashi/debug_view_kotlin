@file:JvmName("LogModule")

package com.sw.debug.view.modules

import com.sw.debug.view.base.AbstractDebugModule


class LogModule private constructor() : AbstractDebugModule<List<String>>(null, null) {

    fun log(msg: String) {
    }

    companion object {
        val instance: LogModule
            get() = LogModule()
    }

}

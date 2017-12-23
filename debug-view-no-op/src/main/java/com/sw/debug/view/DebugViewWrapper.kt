package com.sw.debug.view


import android.app.Application

import com.sw.debug.view.base.AbstractDebugModule


class DebugViewWrapper {


    fun init(builder: Builder?) {
    }

    fun show() {
    }

    fun hide() {
    }

    class Builder(private val application: Application) {


        fun modules(debugModules: MutableList<AbstractDebugModule<*>>): Builder {
            return this
        }

        fun modules(debugModule: AbstractDebugModule<*>, vararg otherModule: AbstractDebugModule<*>): Builder {
            return this
        }

        fun bgColor(color: Int): Builder {
            return this
        }

        fun viewWidth(viewWidth: Int): Builder {
            return this
        }

        fun logMaxLines(logMaxLines: Int): Builder {
            return this
        }

        fun alwaysShowOverlaySetting(alwaysShowOverlaySetting: Boolean): Builder {
            return this
        }

    }

    companion object {

        val instance: DebugViewWrapper
            get() = DebugViewWrapper()
    }

}

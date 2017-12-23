package com.sw.debug.view.base

import android.view.View
import android.view.ViewGroup

abstract class AbstractDebugModule<T>(protected val dataModule: IDataModule<T>?, private val viewModule: IViewModule<T>?) {

    init {
        this.dataModule?.bind(this.viewModule)
    }

    fun start() {
        dataModule?.bind(viewModule)
        dataModule?.start()
    }

    fun stop() {
        dataModule?.unBind(viewModule)
        dataModule?.stop()
    }

    fun reset() {
        dataModule?.onReset()
    }

    fun createView(root: ViewGroup): View? {
        return viewModule?.onCreateView(root)
    }

}

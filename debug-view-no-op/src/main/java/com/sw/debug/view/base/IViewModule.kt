package com.sw.debug.view.base

import android.view.View
import android.view.ViewGroup

interface IViewModule<T> {

    fun onCreateView(root: ViewGroup): View?

    fun onUpdateView(data: T)

    fun onSetVisibility(visibility: Int)
}

package com.sw.debug.view.base


interface IDataModule<T> {

    fun start()

    fun stop()

    fun onReset()

    fun notifyUpdate()

    fun bind(viewModule: IViewModule<T>?)

    fun unBind(viewModule: IViewModule<T>?)

    fun setVisibility(visibility: Int)
}

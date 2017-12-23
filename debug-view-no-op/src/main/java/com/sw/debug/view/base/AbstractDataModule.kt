package com.sw.debug.view.base


abstract class AbstractDataModule<T> : IDataModule<T> {

    private var viewModule: IViewModule<T>? = null

    protected abstract val data: T
}

package com.sw.debug.view.base


abstract class AbstractDataModule<T> : IDataModule<T> {

    private var viewModule: IViewModule<T>? = null

    protected abstract val data: T

    override fun bind(viewModule: IViewModule<T>?) {
        this.viewModule = viewModule
    }

    override fun unBind(viewModule: IViewModule<T>?) {
        this.viewModule = null
    }

    override fun notifyUpdate() {
        if (viewModule != null) {
            val data = data
            viewModule!!.onUpdateView(data)
        }
    }

    override fun setVisibility(visibility: Int) {
        if (viewModule != null) {
            viewModule!!.onSetVisibility(visibility)
        }
    }
}

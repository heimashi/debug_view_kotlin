@file:JvmName("LogModule")

package com.sw.debug.view.modules

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import com.sw.debug.view.base.AbstractDataModule
import com.sw.debug.view.base.AbstractDebugModule
import com.sw.debug.view.base.IViewModule

import java.util.ArrayList


class LogModule private constructor() : AbstractDebugModule<List<String>>(LogDataModule(LOG_MAX_LINES), LogViewModule()) {

    private val logDataModule: LogDataModule

    init {
        logDataModule = dataModule as LogDataModule
    }

    private object SingletonHolder {
        val instance = LogModule()
    }

    fun setLogMaxLines(maxLines: Int) {
        logDataModule.setMaxLines(maxLines)
    }

    fun log(msg: String) {
        logDataModule.log(msg)
    }


    /*
    * log data
    * */
    private class LogDataModule internal constructor(private var maxLines: Int) : AbstractDataModule<List<String>>() {

        private val handler = Handler(Looper.getMainLooper())

        private val logDataList: MutableList<String>

        private var isStarted: Boolean = false

        override val data: List<String>
            get() = logDataList

        init {
            logDataList = ArrayList()
        }

        internal fun setMaxLines(maxLines: Int) {
            this.maxLines = maxLines
        }

        override fun start() {
            isStarted = true
        }

        override fun stop() {
            logDataList.clear()
            isStarted = false
        }

        override fun onReset() {
            logDataList.clear()
            handler.post {
                setVisibility(View.VISIBLE)
                notifyUpdate()
            }
        }

        internal fun log(msg: String) {
            if (TextUtils.isEmpty(msg)) {
                return
            }
            if (!isStarted) {
                return
            }
            while (logDataList.size >= maxLines) {
                logDataList.removeAt(0)
            }
            logDataList.add(msg)

            handler.post { notifyUpdate() }
        }
    }


    /*
    * Log view
    * */
    private class LogViewModule : IViewModule<List<String>> {

        private var listView: ListView? = null
        private var logAdapter: LogAdapter? = null

        override fun onCreateView(root: ViewGroup): View? {
            listView = ListView(root.context)
            listView?.visibility = View.VISIBLE
            listView?.divider = null
            listView?.dividerHeight = 0
            return listView
        }

        override fun onUpdateView(data: List<String>) {
            if (listView != null) {
                logAdapter = LogAdapter(data, listView!!.context)
                listView!!.adapter = logAdapter
            }
        }

        override fun onSetVisibility(visibility: Int) {
            if (listView != null) {
                listView!!.visibility = visibility
            }
        }
    }

    private class LogAdapter internal constructor(private val data: List<String>, private val context: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertViewNew = convertView
            if (convertViewNew == null) {
                convertViewNew = TextView(context)
            }
            val msg = data[position]

            val textView = convertViewNew as TextView?
            textView!!.textSize = 9f
            textView.maxLines = 3
            textView.text = msg
            return convertViewNew
        }

    }

    companion object {

        private val LOG_MAX_LINES = 10

        val instance: LogModule
            get() = SingletonHolder.instance
    }


}

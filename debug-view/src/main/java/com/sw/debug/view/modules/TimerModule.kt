@file:JvmName("TimerModule")

package com.sw.debug.view.modules

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView

import com.sw.debug.view.base.AbstractDataModule
import com.sw.debug.view.base.AbstractDebugModule
import com.sw.debug.view.base.IViewModule

import java.util.ArrayList
import java.util.HashMap


class TimerModule private constructor() : AbstractDebugModule<List<String>>(TimerDataModule(LOG_MAX_LINES), TimerViewModule()) {

    private val timerDataModule: TimerDataModule

    init {
        timerDataModule = dataModule as TimerDataModule
    }

    private object SingletonHolder {
        val instance = TimerModule()
    }

    fun setLogMaxLines(maxLines: Int) {
        timerDataModule.setMaxLines(maxLines)
    }

    fun begin(obj: Any) {
        timerDataModule.begin(obj)
    }

    fun end(obj: Any) {
        timerDataModule.end(obj)
    }


    /*
    * Timer data
    * */
    private class TimerDataModule internal constructor(private var maxLines: Int) : AbstractDataModule<List<String>>() {

        private val handler = Handler(Looper.getMainLooper())

        private val timerDataMap: HashMap<ClassModel, Long>

        private val timerList: MutableList<String>

        private var isStarted: Boolean = false

        override val data: List<String>
            get() = timerList

        init {
            timerList = ArrayList()
            timerDataMap = HashMap()
        }

        internal fun setMaxLines(maxLines: Int) {
            this.maxLines = maxLines
        }

        override fun start() {
            isStarted = true
        }

        override fun stop() {
            timerList.clear()
            timerDataMap.clear()
            isStarted = false
        }

        override fun onReset() {
            timerList.clear()
            timerDataMap.clear()
            handler.post {
                setVisibility(View.VISIBLE)
                notifyUpdate()
            }
        }

        internal fun begin(obj: Any?) {
            if (obj == null) return
            timerDataMap.put(ClassModel(obj), System.currentTimeMillis())
        }

        internal fun end(obj: Any?) {
            if (obj == null) return
            var model = ClassModel(obj)
            if (timerDataMap.containsKey(model)) {
                val end = System.currentTimeMillis()
                val begin = timerDataMap[model] as Long
                timerDataMap.remove(model)
                val msg = getCost(begin, end) ?: return
                while (timerList.size >= maxLines) {
                    timerList.removeAt(0)
                }
                timerList.add(obj.javaClass.simpleName + ":" + msg)
                handler.post { notifyUpdate() }
            }
        }

        internal fun getCost(start: Long, end: Long): String? {
            if (start in 1..(end - 1)) {
                val cost = end - start
                return cost.toString() + "ms"
            }
            return null
        }
    }

    private class ClassModel internal constructor(`object`: Any) {
        internal var hashCode: Int = 0
        internal var className: String

        init {
            hashCode = `object`.hashCode()
            className = `object`.javaClass.name
        }

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false

            val that = o as ClassModel?

            return if (hashCode != that?.hashCode) false else className == that.className
        }

        override fun hashCode(): Int {
            var result = hashCode
            result = 31 * result + className.hashCode()
            return result
        }
    }

    /*
    * Timer view
    * */
    private class TimerViewModule : IViewModule<List<String>> {

        private var listView: ListView? = null
        private var timerAdapter: TimerAdapter? = null

        override fun onCreateView(root: ViewGroup): View? {
            listView = ListView(root.context)
            listView?.divider = null
            listView?.dividerHeight = 0
            return listView
        }

        override fun onUpdateView(data: List<String>) {
            if (listView != null) {
                timerAdapter = TimerAdapter(data, listView!!.context)
                listView!!.adapter = timerAdapter
            }
        }

        override fun onSetVisibility(visibility: Int) {
            if (listView != null) {
                listView!!.visibility = visibility
            }
        }
    }

    private class TimerAdapter internal constructor(private val data: List<String>, private val context: Context) : BaseAdapter() {

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
            var convertView = convertView
            if (convertView == null) {
                convertView = TextView(context)
            }
            val msg = data[position]

            val textView = convertView as TextView?
            textView!!.textSize = 10f
            textView.maxLines = 2
            textView.text = msg
            return convertView
        }

    }

    companion object {

        private val LOG_MAX_LINES = 3

        val instance: TimerModule
            get() = SingletonHolder.instance
    }


}

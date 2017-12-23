package com.sw.debug.view.modules

import android.app.ActivityManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.sw.debug.view.base.AbstractDataModule
import com.sw.debug.view.base.AbstractDebugModule
import com.sw.debug.view.base.IViewModule
import com.sw.debug.view.datas.MemInfo

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class MemInfoModule(context: Context) : AbstractDebugModule<MemInfo>(MemInfoDataModule(context, DEFAULT_INTERVAL), MemInfoViewModule()) {


    /*
    * mem info data
    * */
    private class MemInfoDataModule(context: Context, private val interval: Int) : AbstractDataModule<MemInfo>() {

        private val handler = Handler(Looper.getMainLooper())

        private val am: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        lateinit override var data: MemInfo
            private set

        private val runnable = object : Runnable {
            override fun run() {
                val systemMemInfo = ActivityManager.MemoryInfo()
                am.getMemoryInfo(systemMemInfo)
                val processMemInfo = am.getProcessMemoryInfo(intArrayOf(Process.myPid()))[0]
                data = MemInfo(systemMemInfo, processMemInfo)
                notifyUpdate()
                handler.postDelayed(this, interval.toLong())
            }
        }


        override fun start() {
            handler.post(runnable)
        }

        override fun stop() {
            handler.removeCallbacks(runnable)
        }

        override fun onReset() {

        }
    }


    /*
   * mem info view
   * */
    private class MemInfoViewModule : IViewModule<MemInfo> {

        private var textView: TextView? = null


        override fun onUpdateView(data: MemInfo) {
            val systemMemInfo = data.systemMemInfo
            val procMemInfo = data.processMemInfo
            if (textView != null) {
                val builder = StringBuilder()
                builder.append("Mem:")
                        .append(DECIMAL_FORMAT.format((systemMemInfo.availMem / 1048576f).toDouble()))
                        .append(" Pss:")
                        .append(DECIMAL_FORMAT.format((procMemInfo.totalPss / 1024f).toDouble()))
                        .append(" PD:")
                        .append(DECIMAL_FORMAT.format((procMemInfo.totalPrivateDirty / 1024f).toDouble()))
                        .append(" (MB)")
                textView!!.text = builder
            }
        }

        override fun onCreateView(root: ViewGroup): View? {
            textView = TextView(root.context)
            textView!!.textSize = 14f
            return textView
        }

        override fun onSetVisibility(visibility: Int) {
            if (textView != null) {
                textView!!.visibility = visibility
            }
        }

        companion object {


            private val DECIMAL_FORMAT = DecimalFormat("0.0",
                    DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        }
    }

    companion object {

        private val DEFAULT_INTERVAL = 3000
    }

}

package com.sw.debug.view

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder

import com.sw.debug.view.base.AbstractDebugModule

import java.util.ArrayList

import com.sw.debug.view.extensions.log

internal class DebugViewService : Service() {

    private val binder = LocalBinder()
    private var debugModules: List<AbstractDebugModule<*>> = ArrayList()
    private var debugViewManager: DebugViewManager? = null
    private var modulesStarted: Boolean = false

    internal inner class LocalBinder : Binder() {
        val service: DebugViewService
            get() = this@DebugViewService
    }

    override fun onCreate() {
        log("service onCreate()")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // no need to restart this service
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        log("service onDestroy()")
        stopModules()
        debugViewManager?.hideDebugView()
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        log("service onTaskRemoved()")
        stopSelf()
    }

    fun setDebugModules(debugModules: List<AbstractDebugModule<*>>) {
        this.debugModules = debugModules
    }

    internal fun setDebugViewManager(debugViewManager: DebugViewManager?) {
        this.debugViewManager = debugViewManager
        debugViewManager?.showDebugView()
    }

    fun startModules() {
        if (!modulesStarted) {
            for (debugModule in debugModules) {
                debugModule.start()
            }
            modulesStarted = true
        }
    }

    fun stopModules() {
        if (modulesStarted) {
            for (debugModule in debugModules) {
                debugModule.stop()
            }
            modulesStarted = false
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, DebugViewService::class.java)
        }
    }

}

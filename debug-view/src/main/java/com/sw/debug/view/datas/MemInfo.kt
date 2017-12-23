package com.sw.debug.view.datas

import android.app.ActivityManager
import android.os.Debug

class MemInfo(val systemMemInfo: ActivityManager.MemoryInfo,
              val processMemInfo: Debug.MemoryInfo)
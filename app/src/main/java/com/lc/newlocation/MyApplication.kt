package com.lc.newlocation

import android.app.Application
import com.baidu.mapapi.SDKInitializer

/**
@packageName com.lc.newlocation
@author admin
@date 2020/3/9
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SDKInitializer.initialize(this)
    }
}
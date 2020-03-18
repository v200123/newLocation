package com.lc.newlocation

import android.app.Application
import android.content.Context
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.ModuleName
import com.baidu.mapapi.OpenLogUtil
import com.baidu.mapapi.SDKInitializer

/**
@packageName com.lc.newlocation
@author admin
@date 2020/3/9
 */
class MyApplication : Application() {


object text01 {
    val MyContext:Context  = MyApplication()
    public fun getContext():Context{
        return MyContext
    }
}


    override fun onCreate() {
        super.onCreate()
        SDKInitializer.initialize(this)
        SDKInitializer.setCoordType(CoordType.BD09LL)
//        OpenLogUtil.setModuleLogEnable(ModuleName.TILE_OVERLAY_MODULE,true)
    }


}
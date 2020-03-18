package com.lc.newlocation.mvp

import com.lc.mvp.IBaseView
import com.lc.mvp.IFragmentView
import com.lc.newlocation.bean.BlueToothBean

/**
@packageName com.lc.newlocation.mvp
@author admin
@date 2020/3/13
 */
interface IBluetoothView :IFragmentView {
    fun showError(information:String)
    fun RegisterBroadcast()
    fun addList(blueToothBean: BlueToothBean)
    fun showInformation(information: String)

}
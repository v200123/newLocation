package com.lc.newlocation.mvp.presenter

import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.lc.mvp.BasePresenter
import com.lc.mvp.IBasePresenter
import com.lc.newlocation.mvp.IBluetoothView

/**
@packageName com.lc.newlocation.mvp.presenter
@author admin
@date 2020/3/13
 */
class BluetoothPresenter : BasePresenter<IBluetoothView>() {

    fun openBlueTooth(context: Context) {
        val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE)
                as BluetoothManager
        val adapter = bluetoothManager.adapter
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE).takeIf { true }
            .also {
                mView?.showError("仅支持BLE的设备")
            }.apply {
            if (adapter == null) {
                mView?.showError("没有蓝牙设备")
            }
            if (!adapter.isEnabled) {
                adapter.enable()
            }
            if (adapter.startDiscovery()) {
                Log.d("蓝牙", "开始了")
            } else {
                Log.d("蓝牙", "蓝牙失败了")
            }
        }

    }
}

class BlueDevice : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
    }

}
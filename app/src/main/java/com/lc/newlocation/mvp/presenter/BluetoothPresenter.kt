package com.lc.newlocation.mvp.presenter

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import com.lc.mvp.BasePresenter
import com.lc.newlocation.mvp.IBluetoothView
import es.dmoral.toasty.Toasty


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
                mView?.showError("支持BLE的设备")
            }.apply {
                if (adapter == null) {
                    mView?.showError("没有蓝牙设备")
                } else


                    if (!adapter.isEnabled) {
                        adapter.enable()

                    } else {
                        mView?.RegisterBroadcast()
                        adapter.bluetoothLeScanner.startScan(object : ScanCallback(){
                            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                                super.onScanResult(callbackType, result)
                                mView?.showError("获取到了蓝牙")
                            }

                            override fun onScanFailed(errorCode: Int) {
                                super.onScanFailed(errorCode)
                                mView?.showError(errorCode.toString())
                            }

                            override fun onBatchScanResults(results: MutableList<ScanResult>?) {
                                super.onBatchScanResults(results)
                                mView?.showError("获取到了蓝牙列表")
                            }
                        })
//                        if (adapter.startDiscovery()) {
//                            Log.d("蓝牙", "开始了")
//
//                        } else {
//                            Log.d("蓝牙", "蓝牙失败了")
//                        }
                    }
            }

    }
}


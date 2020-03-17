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
import android.util.AndroidException
import android.util.Log
import com.lc.mvp.BasePresenter
import com.lc.newlocation.R
import com.lc.newlocation.bean.BlueToothBean
import com.lc.newlocation.mvp.IBluetoothView
import com.lc.newlocation.myInterface.BlueToothInterface
import es.dmoral.toasty.Toasty
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Logger


/**
@packageName com.lc.newlocation.mvp.presenter
@author admin
@date 2020/3/13
 */
class BluetoothPresenter : BasePresenter<IBluetoothView>() {

    var blueToothInterface: BlueToothInterface? = null

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
                        val scanCallback = object : ScanCallback() {
                            override fun onScanFailed(errorCode: Int) {
                                super.onScanFailed(errorCode)
                                mView?.showError(errorCode.toString())
                            }

                            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                                super.onScanResult(callbackType, result)
                                val device = result?.device
                                if (device != null) {
                                    mView?.addList(
                                        BlueToothBean(
                                            if (device.name == null) result.scanRecord?.deviceName else device.name
                                            , device.address, device.bondState, R.drawable.bluetooth
                                        )
                                    )
                                }
                            }
                        }

                        adapter.bondedDevices.forEach {
                            mView?.addList(
                                BlueToothBean(
                                    it.name, it.address, it.bondState,
                                    if (it.type == 1) R.drawable.bluetooth_type_01 else R.drawable.bluetooth
                                )
                            )
                        }

                        mView?.RegisterBroadcast()
                        Observable.just(100L).map {
                            adapter.startDiscovery()
                            return@map it
                        }.delay(10L, TimeUnit.MICROSECONDS).map {
                            adapter.cancelDiscovery()
                            adapter.bluetoothLeScanner.startScan(scanCallback)
                        }.delay(10L,TimeUnit.MICROSECONDS)
                            .subscribeOn(Schedulers.single())
                            .subscribe({ adapter.bluetoothLeScanner.startScan(scanCallback)
                            },{ mView?.showError("出错了")})
                        adapter.startDiscovery()


//                        adapter.bluetoothLeScanner.startScan(scanCallback)

                        Observable.timer(15000, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.single())

                            .subscribe(object : Observer<Long> {
                                override fun onNext(t: Long?) {
                                    adapter.bluetoothLeScanner.stopScan(scanCallback)
                                    adapter.cancelDiscovery()
                                    Log.d("分发", "完毕了")
                                    mView?.showError("获取完毕")

                                }

                                override fun onError(e: Throwable?) {

                                }

                                override fun onComplete() {
                                    mView?.showError("完毕")
                                }

                                override fun onSubscribe(d: Disposable?) {

                                }

                            })

                    }
            }

    }

    fun connect(mac: String) {
        mView?.showLoading()
        if (!blueToothInterface!!.connection(mac)) {
            if (blueToothInterface!!.connection(mac)) {
                mView?.showError("蓝牙连接错误")
            } else {
                mView?.showError("蓝牙连接成功")
            }
        } else {
            mView?.showError("蓝牙连接成功")
        }
        mView?.hideLoading()
    }


    fun send(message : String){
        blueToothInterface?.sendmsg(message.toByteArray())
    }
}


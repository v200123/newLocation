package com.lc.newlocation.myInterface.impl

import android.bluetooth.*
import android.util.Log
import com.lc.newlocation.myInterface.BlueToothInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

/**
@packageName com.lc.newlocation.myInterface.impl
@author admin
@date 2020/3/16
 */
class TraditionBlueToothImpl : BlueToothInterface {
    private lateinit var socket: BluetoothSocket
    override fun connection(mac: String): Boolean {
//            bluetoothmanager.adapter.getRemoteDevice(mac)
        var isSuccess = false
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        socket = bluetoothAdapter.getRemoteDevice(mac)
            .createRfcommSocketToServiceRecord(
                UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
            )
        Observable.just(1).map {   bluetoothAdapter?.cancelDiscovery()
                Log.d("蓝牙获取入口","获取入口中")
                socket.connect() }.subscribeOn(Schedulers.newThread())
            .subscribe({
                isSuccess = true
            }, { e ->
                socket.close()
                Log.e("蓝牙", e.message)
                isSuccess = false

            })

        return isSuccess
    }

    override fun disConnection() {
        socket.close()
    }

    override fun sendmsg(msg: ByteArray) {

      Observable.just(msg).subscribeOn(Schedulers.single()).subscribe({ socket.outputStream .apply { write(msg)
      flush()

      }
      },{
          Log.d("蓝牙传输","传输失败")
      })

    }


    class bluetoothGattCallback : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            // status 用于返回操作是否成功,会返回异常码。
            // newState 返回连接状态，如BluetoothProfile#STATE_DISCONNECTED、BluetoothProfile#STATE_CONNECTED
            when (status) {
                BluetoothGatt.GATT_SUCCESS -> {

                }
            }
        }
    }
}
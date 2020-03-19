package com.lc.newlocation.myInterface.impl

import android.bluetooth.*
import android.util.Log
import com.lc.newlocation.myInterface.BlueToothInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.DataOutputStream
import java.io.IOException
import java.lang.Exception
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
        val remoteDevice = bluetoothAdapter.getRemoteDevice(mac)
        socket = remoteDevice
            .createRfcommSocketToServiceRecord(
                UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
            )

//        if(remoteDevice.bondState != BluetoothDevice.BOND_BONDED) {
        Thread{
            try {
                socket.connect()
                isSuccess = true
            }catch ( e:IOException)
            {
                isSuccess =false
            }
        }.start()
//        }
//        else{
//            bluetoothAdapter?.cancelDiscovery()
//            isSuccess = true
//        }
        return isSuccess
    }

    override fun disConnection() {
        socket.close()
    }

    override fun sendmsg(msg: String) {


        Thread{
            try {
                val outputStream = socket.outputStream
                val dataOutputStream = DataOutputStream(outputStream)
                dataOutputStream.writeUTF(msg)
            }catch (e: Exception)
            {
                    Log.d("蓝牙发送消息出错","${e.message}")
            }

        }.start()
//      Observable.just(1).subscribeOn(Schedulers.newThread()).subscribe({ socket.outputStream?.apply {
//          Log.d("蓝牙传输","获取到了流接口")
//          write(msg)
//      flush()
//
//      }
//      },{
//
//          Log.d("蓝牙传输","传输失败")
//      })

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
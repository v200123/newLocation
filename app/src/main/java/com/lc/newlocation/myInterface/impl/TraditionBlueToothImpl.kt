package com.lc.newlocation.myInterface.impl

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
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
    override fun connection(mac: String, bluetoothDevice:BluetoothDevice) {
//            bluetoothmanager.adapter.getRemoteDevice(mac)
        val socket =
            bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            Observable.empty<Unit>().map { a -> socket.connect() }.subscribeOn(Schedulers.single())

                .subscribe({
                    while (socket.isConnected)
                    {
                        socket.outputStream
                    }
                })

    }


    class bluetoothGattCallback :BluetoothGattCallback(){
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            // status 用于返回操作是否成功,会返回异常码。
            // newState 返回连接状态，如BluetoothProfile#STATE_DISCONNECTED、BluetoothProfile#STATE_CONNECTED
            when(status){
                BluetoothGatt.GATT_SUCCESS ->{

                }
            }
        }
    }
}
package com.lc.newlocation.myInterface.impl

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattDescriptor
import com.lc.newlocation.MyApplication
import com.lc.newlocation.myInterface.BlueToothInterface
import kotlin.reflect.KProperty

/**
@packageName com.lc.newlocation.myInterface.impl
@author admin
@date 2020/3/18
 */
private const val STATE_DISCONNECTED = 0
private const val STATE_CONNECTING = 1
private const val STATE_CONNECTED = 2
class LowBlueToothImpl : BlueToothInterface {
    private var sendResult = false
    private var getResult = false
    private var BleStatus:Int = STATE_DISCONNECTED

    private val gattCallback = object : BluetoothGattCallback(){

        override fun onDescriptorRead(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorRead(gatt, descriptor, status)
           if (status == BluetoothGatt.GATT_SUCCESS) {
                    getResult = true
            }
        }

        override fun onDescriptorWrite(
            gatt: BluetoothGatt?,
            descriptor: BluetoothGattDescriptor?,
            status: Int
        ) {
            super.onDescriptorWrite(gatt, descriptor, status)
            if (status == BluetoothGatt.GATT_SUCCESS) {
                sendResult = true
            }
        }
    }

    private val adapter:BluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    override fun connection(mac: String): Boolean {
        val connectGatt = adapter.getRemoteDevice(mac)
            .connectGatt(MyApplication.text01.getContext(), true, gattCallback)
        return connectGatt!=null
    }

    override fun disConnection() {
        TODO("Not yet implemented")
    }

    override fun sendmsg(msg: ByteArray) {
        TODO("Not yet implemented")
    }
}


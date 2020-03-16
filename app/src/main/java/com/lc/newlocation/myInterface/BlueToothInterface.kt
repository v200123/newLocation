package com.lc.newlocation.myInterface

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager

/**
@packageName com.lc.newlocation.myInterface
@author admin
@date 2020/3/16
 */
interface BlueToothInterface {
    fun connection(mac :String, bluetoothDevice: BluetoothDevice)

}
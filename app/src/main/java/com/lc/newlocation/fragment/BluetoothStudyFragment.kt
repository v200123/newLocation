package com.lc.newlocation.fragment

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_STARTED
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.github.jokar.permission.PermissionUtil
import com.lc.mvp.BaseFragment
import com.lc.mvp.IBasePresenter
import com.lc.newlocation.R
import com.lc.newlocation.mvp.IBluetoothView
import com.lc.newlocation.mvp.presenter.BluetoothPresenter
import es.dmoral.toasty.Toasty

/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/13
 */
class BluetoothStudyFragment : BaseFragment<IBluetoothView, BluetoothPresenter>(),IBluetoothView {
    override fun createPresenter(): BluetoothPresenter  = BluetoothPresenter()
    override fun showError(information: String) {
        Toasty.error(context,information).show()
    }

    override fun RegisterBroadcast() {
        context.registerReceiver(BlueDevice(context),IntentFilter(ACTION_DISCOVERY_STARTED))
    }

    override fun initData() {
        PermissionUtil.Builder(this)
            .setPermissions(Manifest.permission.BLUETOOTH)
            .setPermissions(Manifest.permission.BLUETOOTH_ADMIN)
            .request()

    }

    override fun initView() {
        mPresenter.openBlueTooth(context)
    }

    override fun getLayoutId() = R.layout.fragment_bluetooth_study

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }


    class BlueDevice (var context1 : Context): BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("蓝牙", "扫描开始了")
            when(intent?.action){
                ACTION_DISCOVERY_STARTED ->{
             Log.d("蓝牙","蓝牙扫描开始")
                }
                BluetoothDevice.ACTION_FOUND ->{
                    Toasty.success(context1,"扫描到了").show()
                }
            }
        }

    }
}
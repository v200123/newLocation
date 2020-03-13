package com.lc.newlocation.fragment

import android.Manifest
import com.github.jokar.permission.PermissionUtil
import com.lc.mvp.BaseFragment
import com.lc.mvp.IBasePresenter
import com.lc.newlocation.R
import com.lc.newlocation.mvp.IBluetoothView
import com.lc.newlocation.mvp.presenter.BluetoothPresenter

/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/13
 */
class BluetoothStudyFragment : BaseFragment<IBluetoothView, BluetoothPresenter>() {
    override fun createPresenter(): BluetoothPresenter  = BluetoothPresenter()

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
}
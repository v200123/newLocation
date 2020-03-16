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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.jokar.permission.PermissionUtil
import com.lc.mvp.BaseFragment
import com.lc.mvp.IBasePresenter
import com.lc.newlocation.R
import com.lc.newlocation.bean.BlueToothBean
import com.lc.newlocation.mvp.IBluetoothView
import com.lc.newlocation.mvp.presenter.BluetoothPresenter
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_bluetooth_study.*
import java.util.logging.Logger

/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/13
 */
class BluetoothStudyFragment : BaseFragment<IBluetoothView, BluetoothPresenter>(), IBluetoothView {

    lateinit var blueAdapter: BlueAdapter
    override fun createPresenter(): BluetoothPresenter = BluetoothPresenter()
    override fun showError(information: String) {
        Toasty.error(context, information).show()
    }

    override fun RegisterBroadcast() {
        context.registerReceiver(BlueDevice(context),IntentFilter(BluetoothDevice.ACTION_FOUND))
    }


    override fun addList(blueToothBean: BlueToothBean) {
        var isAdded = false
        pull_to_refresh.finishRefresh()
        blueAdapter.data.forEach() {
            if (it.address == blueToothBean.address) {
                isAdded = true
                if (it.name != blueToothBean.name) {
                    it.name = blueToothBean.name
                    blueAdapter.notifyDataSetChanged()
                }
            }
        }
        if (!isAdded)
            blueAdapter.addData(blueToothBean)

    }

    override fun initData() {
        PermissionUtil.Builder(this)
            .setPermissions(Manifest.permission.BLUETOOTH)
            .setPermissions(Manifest.permission.BLUETOOTH_ADMIN)
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .request()

    }

    override fun initView() {
        pull_to_refresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {mPresenter.openBlueTooth(context)
                blueAdapter.replaceData(ArrayList<BlueToothBean>())
            }
            override fun onMoveTarget(offset: Int) {
            }
        })
        mPresenter.openBlueTooth(context)
        blueAdapter = BlueAdapter(R.layout.fragment_rv_bluetooth_item)
        rv_BlueTooth.apply {
            adapter = blueAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getLayoutId() = R.layout.fragment_bluetooth_study


   inner class BlueDevice (var context1 : Context): BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                ACTION_DISCOVERY_STARTED ->{
                }
                BluetoothDevice.ACTION_FOUND ->{
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        blueAdapter.addData(
                            BlueToothBean(if(device.name==null) "不知名" else device.name
                            ,device.address,device.bondState,if(device.type == 1) R.drawable.bluetooth_type_01 else
                            R.drawable.bluetooth)
                        )
                }
            }
        }
    }

    class BlueAdapter(LayoutId: Int) : BaseQuickAdapter<BlueToothBean, BaseViewHolder>(LayoutId) {


        override fun convert(helper: BaseViewHolder, item: BlueToothBean) {
            var result = ""
            when(item.State)
            {
                BluetoothDevice.BOND_NONE ->{result = "未配对"}
                BluetoothDevice.BOND_BONDED -> { result = "已配对"}
            }
            helper.setText(R.id.tv_toothName, item.name).setText(R.id.tv_toothAddress, item.address)
                .setImageDrawable(R.id.imageView,ContextCompat.getDrawable(context,item.drawableId))
                .setText(R.id.blue_bond,result                )
        }

    }




}
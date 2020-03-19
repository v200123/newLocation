package com.lc.newlocation.fragment

import android.Manifest
import android.bluetooth.BluetoothAdapter.*
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.jokar.permission.PermissionUtil
import com.lc.mvp.BaseFragment
import com.lc.newlocation.R
import com.lc.newlocation.bean.BlueToothBean
import com.lc.newlocation.mvp.IBluetoothView
import com.lc.newlocation.mvp.presenter.BluetoothPresenter
import com.lc.newlocation.myInterface.impl.TraditionBlueToothImpl
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_bluetooth_study.*

/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/13
 */
class BluetoothStudyFragment : BaseFragment<IBluetoothView, BluetoothPresenter>(), IBluetoothView {

    lateinit var blueAdapter: BlueAdapter
    override fun createPresenter(): BluetoothPresenter = BluetoothPresenter()
    override fun showError(information: String) {
        if (Looper.myLooper() != Looper.getMainLooper())
            Looper.prepare()
        Toasty.error(context, information).show()
    }

    override fun RegisterBroadcast() {
        context.registerReceiver(BlueDevice(), IntentFilter(BluetoothDevice.ACTION_FOUND))
        context.registerReceiver(BlueDevice(), IntentFilter(ACTION_STATE_CHANGED))
    }


    override fun addList(blueToothBean: BlueToothBean) {
        var isAdded = true
        pull_to_refresh.finishRefresh()

        for (BlueToothBean in blueAdapter.data) {
            if (blueToothBean.address == blueToothBean.address) {
                isAdded = false
            }
        }
        if (isAdded)
            blueAdapter.addData(blueToothBean)

    }

    override fun showInformation(information: String) {
        Toasty.info(context, information).show()
    }

    override fun initData() {
        PermissionUtil.Builder(this)
            .setPermissions(Manifest.permission.BLUETOOTH)
            .setPermissions(Manifest.permission.BLUETOOTH_ADMIN)
            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .request()
        blueAdapter = BlueAdapter(R.layout.fragment_rv_bluetooth_item)
    }

    override fun initView(view: View) {

        btn_send.setOnClickListener {
          mPresenter.send(et_sendMsg.text.toString())
        }

        btn_goto_Map.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_bluetoothStudyFragment_to_mapFragment)
        }

        pull_to_refresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                mPresenter.openBlueTooth(context)
                blueAdapter.replaceData(ArrayList<BlueToothBean>())

            }

            override fun onMoveTarget(offset: Int) {
            }
        })
        mPresenter.openBlueTooth(context)

        rv_BlueTooth.apply {
            adapter = blueAdapter
            layoutManager = LinearLayoutManager(context)
        }
        blueAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                val blueToothBean = adapter.data[position] as BlueToothBean
                if (blueToothBean.drawableId == R.drawable.bluetooth_type_01)
                    mPresenter.blueToothInterface = TraditionBlueToothImpl()
                else
                    mPresenter.blueToothInterface = TraditionBlueToothImpl()
                mPresenter.connect(blueToothBean.address)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_bluetooth_study


    inner class BlueDevice() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_DISCOVERY_STARTED -> {
                }
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        blueAdapter.addData(
                            BlueToothBean(
                                if (device.name == null) "不知名" else device.name
                                ,
                                device.address,
                                device.bondState,
                                if (device.type == 1) R.drawable.bluetooth_type_01 else
                                    R.drawable.bluetooth
                            )
                        )
                    }
                }
                ACTION_STATE_CHANGED -> {
                    when (intent.getIntExtra(EXTRA_STATE, 0)) {
                        STATE_OFF -> {
                            showInformation("蓝牙关闭了")

                        }
                        STATE_ON -> {
                            showInformation("蓝牙开启了")

                        }
                    }
                }
            }
        }
    }

    class BlueAdapter(LayoutId: Int) : BaseQuickAdapter<BlueToothBean, BaseViewHolder>(LayoutId) {

        override fun convert(helper: BaseViewHolder, item: BlueToothBean) {
            var result = ""
            when (item.State) {
                BluetoothDevice.BOND_NONE -> {
                    result = "未配对"
                }
                BluetoothDevice.BOND_BONDED -> {
                    result = "已配对"
                }
            }
            helper.setText(R.id.tv_toothName, item.name).setText(R.id.tv_toothAddress, item.address)
                .setImageDrawable(
                    R.id.imageView,
                    ContextCompat.getDrawable(context, item.drawableId)
                )
                .setText(R.id.blue_bond, result)
        }

    }


}
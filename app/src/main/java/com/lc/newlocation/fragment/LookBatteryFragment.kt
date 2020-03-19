package com.lc.newlocation.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.view.View
import com.lc.mvp.BaseFragment
import com.lc.newlocation.R
import com.lc.newlocation.mvp.ILookBatteryFragmentView
import com.lc.newlocation.mvp.presenter.LookBatteryFragmentPresenter
import kotlinx.android.synthetic.main.frgment_lookbattery.*

/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/19
 */
class LookBatteryFragment : BaseFragment<ILookBatteryFragmentView, LookBatteryFragmentPresenter>() {
    override fun createPresenter(): LookBatteryFragmentPresenter = LookBatteryFragmentPresenter()

    override fun initData() {

       context. registerReceiver(BatteryVolter(), getFilter());


    }

    override fun initView(view: View) {
        textView.text = "显示充电电流"
    }

    override fun getLayoutId(): Int  = R.layout.frgment_lookbattery

    fun  getFilter():IntentFilter {
        val filter =  IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        return filter}

    inner class BatteryVolter : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if(action == Intent.ACTION_BATTERY_CHANGED)
            {
                val intExtra = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)
                textView.text = "$intExtra"
//                // 当前电池的电压
//                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
//                -1);
//                // 电池的健康状态
//                int health = intent
//                        .getIntExtra(BatteryManager.EXTRA_HEALTH, -1);

            }
        }
    }}

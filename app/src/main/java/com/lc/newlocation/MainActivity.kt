package com.lc.newlocation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.baidu.mapapi.map.MapView
import com.lc.mvp.BaseActivity
import com.lc.newlocation.fragment.BluetoothStudyFragment
import com.lc.newlocation.fragment.MapFragment
import com.lc.newlocation.mvp.IMainView
import com.lc.newlocation.mvp.presenter.IMainPresenter

class MainActivity : BaseActivity<IMainView, IMainPresenter>() {
    private lateinit var mBaiduMap: MapView
    private var mapFragment:Fragment = MapFragment()
    private val blueTooth:Fragment by lazy { BluetoothStudyFragment() }

    override fun createPresenter(): IMainPresenter? = IMainPresenter()

    override fun getContext(): Context = this

    override fun getLayoutId(): Int  = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mBaiduMap = baiduMap
        showFragment(blueTooth,R.id.map_container)
    }

    override fun onResume() {
        super.onResume()
//        mBaiduMap.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
//        mBaiduMap.onDestroy()
    }

    override fun onPause() {
        super.onPause()
//        mBaiduMap.onPause()
    }

}

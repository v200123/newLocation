package com.lc.newlocation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapView
import com.lc.mvp.BaseActivity
import com.lc.newlocation.mvp.IMainView
import com.lc.newlocation.mvp.presenter.IMainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<IMainView, IMainPresenter>() {
    private lateinit var mBaiduMap: MapView


    override fun createPresenter(): IMainPresenter? = IMainPresenter()

    override fun getContext(): Context = this

    override fun getLayoutId(): Int  = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaiduMap = baiduMap
    }

    override fun onResume() {
        super.onResume()
        mBaiduMap.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBaiduMap.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mBaiduMap.onPause()
    }

}

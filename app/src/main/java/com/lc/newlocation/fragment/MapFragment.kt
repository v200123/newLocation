package com.lc.newlocation.fragment

import android.Manifest
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.VersionInfo
import com.baidu.mapapi.common.BaiduMapSDKException
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.github.jokar.permission.PermissionUtil
import com.lc.mvp.BaseFragment
import com.lc.newlocation.R
import com.lc.newlocation.Show
import com.lc.newlocation.mvp.IMapFragment
import com.lc.newlocation.mvp.presenter.IMapPresenter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.popup.QMUINormalPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import com.qmuiteam.qmui.widget.popup.QMUIPopups
import kotlinx.android.synthetic.main.fragment_map.*


/**
@packageName com.lc.newlocation.fragment
@author admin
@date 2020/3/10
 */
class MapFragment : BaseFragment<IMapFragment, IMapPresenter>() {
    private lateinit var mBaiduMap: MapView
    private lateinit var baiduMap: BaiduMap
    private lateinit var locationClient: LocationClient
    private lateinit var show: QMUINormalPopup<QMUIPopup>
    private val items = mutableListOf<String>("跟随态", "正常态", "罗盘态")
    override fun createPresenter(): IMapPresenter = IMapPresenter()

    override fun initData() {
        PermissionUtil.Builder(context as FragmentActivity)
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .setPermissions(Manifest.permission.READ_PHONE_STATE)
            .request()
        locationClient = LocationClient(context)
        locationClient.locOption = LocationClientOption().apply {
            openGps = true
            setNeedDeviceDirect(true)
            setIsNeedAddress(true)
            mIsNeedDeviceDirect = true
            locationMode = LocationClientOption.LocationMode.Hight_Accuracy
            enableSimulateGps = true
            coorType = "bd09ll"
            scanSpan = 1000
        }


    }


    override fun initView(view: View) {
        mBaiduMap = map_fragment
        baiduMap = mBaiduMap.map
        baiduMap.isMyLocationEnabled = true
        baiduMap.setMyLocationConfiguration(
            MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING
                , true, null
            )
        )

        showAddress.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_mapFragment_to_lookBatteryFragment)
        }

        baiduMap.setOnMapLongClickListener {
            val decodeResource =
            BitmapDescriptorFactory.fromResource( R.drawable.icon_openmap_mark)
            baiduMap.addOverlay(MarkerOptions().position(it).icon(decodeResource).perspective(true)
                .anchor(0.5f,0.5f)) }
        showAddress.text = "当前定位的版本${locationClient.version}，地图的版本是${VersionInfo.getApiVersion()}\n版本描述信息是" +
                "${VersionInfo.VERSION_DESC}"
        change_set.setOnClickListener {
            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(context, R.layout.simple, R.id.tv_Item, items)
            val onItemClickListener =
                AdapterView.OnItemClickListener() { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    when (i) {
                        0 -> {
                            baiduMap.setMyLocationConfiguration(
                                MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.FOLLOWING
                                    ,
                                    true, null
                                )
                            )
                        }
                        1 -> {
                            baiduMap.setMyLocationConfiguration(
                                MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.NORMAL
                                    ,
                                    true, null
                                )
                            )
                        }
                        else -> {
                            baiduMap.setMyLocationConfiguration(
                                MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.COMPASS
                                    ,
                                    true, null
                                )
                            )
                        }
                    }
                    show.dismiss()
                }

            show = QMUIPopups.listPopup(
                    context, QMUIDisplayHelper.dp2px(context, 100),
                    QMUIDisplayHelper.dp2px(context, 300), adapter, onItemClickListener
                )
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .shadow(true)
                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
                .offsetYIfTop(QMUIDisplayHelper.dp2px(context, 60))
                .arrow(true)
                .offsetX(70)
                .arrowSize(
                    QMUIDisplayHelper.dp2px(context, 15),
                    QMUIDisplayHelper.dp2px(context, 15)
                )
                .onDismiss {
                    Toast.makeText(context, "显示", Toast.LENGTH_SHORT).show()
                }.radius(QMUIDisplayHelper.dp2px(context, 8)).show(change_set)
        }
        locationClient.registerLocationListener(MyLocationListner())
        locationClient.start()
    }

    override fun getLayoutId(): Int = R.layout.fragment_map


    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop()
        baiduMap.isMyLocationEnabled = false
        mBaiduMap.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mBaiduMap.onPause()
    }

    override fun onResume() {
        mBaiduMap.onResume()
        super.onResume()
    }


    inner class MyLocationListner : BDAbstractLocationListener() {
        override fun onReceiveLocation(p0: BDLocation?) {
            if (p0 == null) {
                return
            }
            val locData = MyLocationData.Builder()
                .accuracy(p0.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(p0.direction).latitude(p0.latitude)
                .longitude(p0.longitude).build()
//            showMapStatus.setText("当前的精度为${p0.radius},是否发生错误${p0.locType}")
            baiduMap.setMyLocationData(locData)

        }


    }
}


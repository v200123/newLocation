package com.lc.mvp

import android.view.View

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/10
 */
interface IFragmentView : IBaseView {
   fun initData()
    fun initView(view: View)
}
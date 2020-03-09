package com.lc.mvp

import android.content.Context

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
interface IBaseView {
    fun getContext():Context
    fun getLayoutId():Int
    fun showLoading()
    fun hideLoading()
}